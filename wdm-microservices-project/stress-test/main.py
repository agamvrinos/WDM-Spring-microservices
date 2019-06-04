import pickle
import random

from locust import HttpLocust, TaskSet, task


class User(TaskSet):

    def setup(self):
        create_response = self.client.post(
            url=f"/api/orders/orders/create/{self.locust.user_id}")
        self.order_id = create_response.json()

    @task(5)
    def add_item(self):
        response = self.client.post(
            url=f"/api/orders/orders/addItem/{self.order_id}/{random.choice(self.locust.item_ids)}",
            json={"amount": random.randint(1, 5)}
        )
        if response.status_code == 400:
            response.success()

    @task(1)
    def check_status(self):
        response = self.client.get(
            url=f"/api/orders/orders/find/{self.order_id}"
        )
        if response.status_code == 400:
            response.success()

    @task(1)
    def checkout(self):
        response = self.client.post(
            url=f"/api/orders/orders/checkout/{self.order_id}"
        )
        if response.status_code == 400:
            response.success()
        self.interrupt()


class WebsiteUser(HttpLocust):

    def setup(self):
        self.user_ids = pickle.load("user_id.pickle")
        self.item_ids = pickle.load("item_id.pickle")

    task_set = User
    min_wait = 0
    max_wait = 1000
