import sys

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

#############
# Load data #
#############
success_df = pd.read_csv(sys.argv[1], sep=';')
failure_df = pd.read_csv(sys.argv[2], sep=';')

# Discretize data to have its start time on each three seconds.
success_df['start_time'] = (success_df['start_time'] - success_df['start_time'].min()) // 3 * 3
failure_df['start_time'] = (failure_df['start_time'] - failure_df['start_time'].min()) // 3 * 3

# Remove the /api/{service} postfix from the endpoints column.
success_df['endpoint'] = success_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))
failure_df['endpoint'] = failure_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))

#############################
# Create response time plot #
#############################
# Individual request types
sns.relplot(legend=False, aspect=3, x='start_time', y='response_time', hue='endpoint', ci='sd', kind='line', data=success_df)
labels = success_df['endpoint'].unique()
plt.xlabel("Time (s)")
plt.ylabel("Response Time (ms)")
plt.legend(loc='best', labels=labels)
plt.show()

# Aggregate
sns.relplot(legend=False, aspect=3, x='start_time', y='response_time', ci='sd', kind='line', data=success_df)
plt.xlabel("Time (s)")
plt.ylabel("Response Time (ms)")
plt.show()

##############################
# Create total requests plot #
##############################
max_timestamp = success_df['start_time'].max()
timesteps = [3 * x for x in range(max_timestamp // 3 + 1)]

# Count requests per time step per type of endpoint.
individual_counts = success_df.groupby(by=['endpoint', 'start_time'])['response_time'].count()
individual_counts = individual_counts.reindex(
    pd.MultiIndex.from_product([individual_counts.index.levels[0], timesteps],
                               names=['endpoint', 'start_time']),
    fill_value=0).reset_index().rename({'response_time': 'num_requests'}, axis='columns')

# Count requests per time step for all endpoints combined.
aggregated_counts = success_df.groupby(by=['start_time'])['response_time'].count()\
    .reindex(timesteps, fill_value=0)\
    .reset_index()\
    .rename({'response_time': 'num_requests'}, axis='columns')
aggregated_counts['endpoint'] = 'combined'

# Create plot for the number of requests
counts = pd.concat([individual_counts, aggregated_counts], sort=False)
max_count = counts['num_requests'].max()
fig, ax = plt.subplots(figsize=(15, 7.5))
sns.lineplot(data=counts, hue='endpoint', ax=ax, x='start_time', y='num_requests')
plt.xlabel('Time (s)')
plt.ylabel('Number of Requests')
plt.ylim((-0.1, max_count * 1.1))
plt.show()

####################################################
# Create total requests against success ratio plot #
####################################################
# Count number of failed requests
failure_counts = failure_df.groupby(by=['start_time'])['exception'].count().reindex(timesteps, fill_value=0).reset_index().rename({'exception': 'num_errors'}, axis='columns')

# Compute success ratio of the requests.
combined = pd.merge(aggregated_counts, failure_counts, on='start_time')
combined['success_ratio'] = combined.apply(lambda row: 1 - row['num_errors'] / row['num_requests'], axis=1)
sns.lineplot(x='num_requests', y='success_ratio', data=combined)
plt.xlabel('Number of Requests')
plt.ylabel('Percentage of Successful Requests')
plt.show()
