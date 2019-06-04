import requests
import pickle
import sys

gateway_url = sys.argv[1]

with open(sys.argv[2], "rb") as user_file:
    users = pickle.load(user_file)

with open(sys.argv[3], "rb") as item_file:
    items = pickle.load(item_file)

item_ids = list()
for item in items:
    response = requests.post(url=f"{gateway_url}/api/stock/stock/item/create", json=item)
    if response.status_code == 200:
        item_ids.append(response.json())
    else:
        raise ConnectionError(f"Creation of item {item} has failed: {response.status_code}")
with open(sys.argv[5], "wb") as item_id_file:
    pickle.dump(item_ids, item_id_file)


user_ids = list()
for user in users:
    response = requests.post(url=f"{gateway_url}/api/users/users/create", json=user)
    if response.status_code == 200:
        user_ids.append(response.json())
    else:
        raise ConnectionError(f"Creation of user {user} has failed: {response.status_code}")
with open(sys.argv[4], "wb") as user_id_file:
    pickle.dump(user_ids, user_id_file)
