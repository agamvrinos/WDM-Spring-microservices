import pickle
import random

from locust import HttpLocust, TaskSet, task


class User(TaskSet):

    def __init__(self, parent):
        super().__init__(parent=parent)
        self.order_id = 0
        self.user_ids = list()
        self.item_ids = list()

    def on_start(self):
        with open("user_id.pickle", "rb") as user_id_file:
            self.user_ids = pickle.load(user_id_file)
            print(self.user_ids)
        with open("item_id.pickle", "rb") as item_id_file:
            self.item_ids = pickle.load(item_id_file)
        self._get_new_order()

    def _get_new_order(self):
        create_response = self.client.post(
            url=f"/api/orders/orders/create/{random.choice(self.user_ids)}",
            name="/api/orders/orders/create/{user_id}")
        if create_response.status_code == 200:
            self.order_id = create_response.json()

    @task(10)
    def add_item(self):
        response = self.client.post(
            url=f"/api/orders/orders/addItem/{self.order_id}/{random.choice(self.item_ids)}",
            json={"amount": random.randint(1, 5)},
            name="/api/orders/orders/addItem/{order_id}/{item_id}"
        )
        if response.status_code == 400:
            response.success()

    @task(1)
    def check_status(self):
        response = self.client.get(
            url=f"/api/orders/orders/find/{self.order_id}",
            name="/api/orders/orders/find/{order_id}"
        )
        if response.status_code == 400:
            response.success()

    @task(1)
    def checkout(self):
        response = self.client.post(
            url=f"/api/orders/orders/checkout/{self.order_id}",
            name="/api/orders/orders/checkout/{order_id}"
        )
        if response.status_code == 400:
            response.success()
        self._get_new_order()


class WebsiteUser(HttpLocust):
    task_set = User
    min_wait = 0
    max_wait = 1000
