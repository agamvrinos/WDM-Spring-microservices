import pickle
import random
from google.cloud import storage
from locust import HttpLocust, TaskSet, task, events

client = storage.Client.from_service_account_json('locust/key.json')
bucket = client.get_bucket('wdm_locust')


class User(TaskSet):

    def __init__(self, parent):
        super().__init__(parent=parent)
        self.order_id = 0
        self.user_ids = list()
        self.item_ids = list()
        self.outputBlob = None

    def on_start(self):
        user_blob = bucket.get_blob('userid.pickle')
        item_blob = bucket.get_blob('itemid.pickle')

        user_id_pickle_cloud = open("userid.pickle", "wb")
        user_blob.download_to_file(user_id_pickle_cloud)

        item_id_pickle_cloud = open("itemid.pickle", "wb")
        item_blob.download_to_file(item_id_pickle_cloud)

        user_id_pickle_cloud.close()
        item_id_pickle_cloud.close()

        with open("userid.pickle", 'rb') as f:
            self.user_ids = pickle.loads(f.read())
        with open("itemid.pickle", 'rb') as f:
            self.item_ids = pickle.loads(f.read())

        self._get_new_order()

    def _get_new_order(self, previous_id=0):
        while self.order_id == previous_id:
            with self.client.post(
                    url=f"/orders/create/{random.choice(self.user_ids)}",
                    name="/orders/create/{user_id}",
                    catch_response=True) as create_response:
                if create_response.status_code == 200:
                    self.order_id = create_response.json()
                    break

    @task(10)
    def add_item(self):
        with self.client.post(
                url=f"/orders/addItem/{self.order_id}/{random.choice(self.item_ids)}",
                json={"amount": random.randint(1, 5)},
                name="/orders/addItem/{order_id}/{item_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()

    @task(1)
    def check_status(self):
        with self.client.get(
                url=f"/orders/find/{self.order_id}",
                name="/orders/find/{order_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()

    @task(1)
    def checkout(self):
        with self.client.post(
                url=f"/orders/checkout/{self.order_id}",
                name="/orders/checkout/{order_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()
        self._get_new_order()


class WebsiteUser(HttpLocust):
    task_set = User
    min_wait = 500
    max_wait = 500


def locust_error_handler(locust_instance, exception, tb, **kwargs):
    print(f"{locust_instance}: {exception} - {tb}")


def slave_report_handler(client_id, data):
    print(f"Slave {client_id} reports to master.")
    start_time = data['stats_total']['start_time']
    with open(success_filename, "a") as success_csv:
        success_data = [f"{int(start_time)};{request['method']};{request['name']};{response_time}"
                        for request in data['stats']
                        for response_time, amount in request['response_times'].items()
                        for _ in range(amount)]
        success_csv.write('\n'.join(success_data) + ('\n' if success_data else ''))

    with open(failure_filename, "a") as failure_csv:
        failure_data = [f"{int(start_time)};{error['method']};{error['name']};{error['error']}"
                        for error in data['errors'].values()
                        for _ in range(error['occurences'])]
        failure_csv.write('\n'.join(failure_data) + ('\n' if failure_data else ''))


def quitting_handler():
    success_blob = bucket.blob('success.csv')
    success_blob.upload_from_filename(filename='success.csv')
    failure_blob = bucket.blob('failure.csv')
    failure_blob.upload_from_filename(filename='failure.csv')


events.locust_error += locust_error_handler
events.slave_report += slave_report_handler
events.quitting += quitting_handler

success_filename = "success.csv"
with open(success_filename, "w+") as success_csv:
    success_csv.write("start_time;method;endpoint;response_time\n")

failure_filename = "failure.csv"
with open(failure_filename, "w+") as failure_csv:
    failure_csv.write("start_time;method;endpoint;exception\n")
