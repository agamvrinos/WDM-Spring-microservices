import pickle
import random

from locust import HttpLocust, TaskSet, task, events


class User(TaskSet):

    def __init__(self, parent):
        super().__init__(parent=parent)
        self.order_id = 0
        self.user_ids = list()
        self.item_ids = list()

    def on_start(self):
        with open("user_id.pickle", "rb") as user_id_file:
            self.user_ids = pickle.load(user_id_file)
        with open("item_id.pickle", "rb") as item_id_file:
            self.item_ids = pickle.load(item_id_file)
        self._get_new_order()

    def _get_new_order(self):
        with self.client.post(
                url=f"/api/orders/orders/create/{random.choice(self.user_ids)}",
                name="/api/orders/orders/create/{user_id}",
                catch_response=True) as create_response:
            if create_response.status_code == 200:
                self.order_id = create_response.json()

    @task(10)
    def add_item(self):
        with self.client.post(
                url=f"/api/orders/orders/addItem/{self.order_id}/{random.choice(self.item_ids)}",
                json={"amount": random.randint(1, 5)},
                name="/api/orders/orders/addItem/{order_id}/{item_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()

    @task(1)
    def check_status(self):
        with self.client.get(
                url=f"/api/orders/orders/find/{self.order_id}",
                name="/api/orders/orders/find/{order_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()

    @task(1)
    def checkout(self):
        with self.client.post(
                url=f"/api/orders/orders/checkout/{self.order_id}",
                name="/api/orders/orders/checkout/{order_id}",
                catch_response=True) as response:
            if response.status_code == 400:
                response.success()
        self._get_new_order()


class WebsiteUser(HttpLocust):
    task_set = User
    min_wait = 0
    max_wait = 1000


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


events.locust_error += locust_error_handler
events.slave_report += slave_report_handler

success_filename = "success.csv"
with open(success_filename, "w+") as success_csv:
    success_csv.write("start_time,request_type,name,response_time\n")

failure_filename = "failure.csv"
with open(failure_filename, "w+") as failure_csv:
    failure_csv.write("start_time,request_type,name,exception\n")
