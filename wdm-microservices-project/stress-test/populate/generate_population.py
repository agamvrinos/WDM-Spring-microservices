import pickle
import random
import sys

num_users = int(sys.argv[1])
num_items = int(sys.argv[2])
min_credit = int(sys.argv[3])
max_credit = int(sys.argv[4])
min_stock = int(sys.argv[5])
max_stock = int(sys.argv[6])

users = [{"name": f"User{index}",
          "credit": "%d" % random.randint(min_credit, min_credit)}
         for index in range(num_users)]
with open("users.pickle", "wb") as user_file:
    pickle.dump(users, user_file, protocol=pickle.HIGHEST_PROTOCOL)

items = [{"title": f"Item{index}",
          "stock": random.randint(min_stock, max_stock),
          "price": 1}
         for index in range(num_items)]

with open("items.pickle", "wb") as item_file:
    pickle.dump(items, item_file, protocol=pickle.HIGHEST_PROTOCOL)

print(users)
print(items)
