import sys

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

#############
# Load data #
#############
success_df = pd.read_csv(sys.argv[1], sep=';')
failure_df = pd.read_csv(sys.argv[2], sep=';')

# Discretize data to have its start time on each three seconds.
success_df['start_time'] = (success_df['start_time'] - success_df['start_time'].min()) // 3 * 3
failure_df['start_time'] = (failure_df['start_time'] - failure_df['start_time'].min()) // 3 * 3

complete_df = pd.concat([success_df, failure_df], sort=True)

# Remove the /api/{service} postfix from the endpoints column.
# success_df['endpoint'] = success_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))
# failure_df['endpoint'] = failure_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))

#############################
# Create response time plot #
#############################
# Individual request types
sns.relplot(legend=False, aspect=3, x='start_time', y='response_time', hue='endpoint', ci='sd', kind='line', data=success_df)
labels = success_df['endpoint'].unique()
plt.xlabel("Time (s)")
plt.ylabel("Response Time (ms)")
plt.legend(loc='best', labels=labels)
plt.ylim(bottom=-0.1)
plt.savefig('individual_response.png')

# Aggregate
sns.relplot(legend=False, aspect=3, x='start_time', y='response_time', ci='sd', kind='line', data=success_df)
plt.xlabel("Time (s)")
plt.ylabel("Response Time (ms)")
plt.ylim(bottom=-0.1)
plt.xlim(left=0)
plt.savefig('aggregate_response.png')

##############################
# Create total requests plot #
##############################
max_timestamp = complete_df['start_time'].max()
timesteps = [3 * x for x in range(max_timestamp // 3 + 1)]

# Count requests per time step per type of endpoint.
individual_counts = complete_df.groupby(by=['endpoint', 'start_time'])['method'].count()
individual_counts = individual_counts.reindex(
    pd.MultiIndex.from_product([individual_counts.index.levels[0], timesteps],
                               names=['endpoint', 'start_time']),
    fill_value=0).reset_index().rename({'method': 'num_requests'}, axis='columns')

# Count requests per time step for all endpoints combined.
aggregated_counts = complete_df.groupby(by=['start_time'])['method'].count()\
    .reindex(timesteps, fill_value=0)\
    .reset_index()\
    .rename({'method': 'num_requests'}, axis='columns')
aggregated_counts['endpoint'] = 'combined'

# Create plot for the number of requests
counts = pd.concat([individual_counts, aggregated_counts], sort=False)
max_count = counts['num_requests'].max()
fig, ax = plt.subplots(figsize=(15, 7.5))
sns.lineplot(data=counts, hue='endpoint', ax=ax, x='start_time', y='num_requests')
plt.xlabel('Time (s)')
plt.ylabel('Number of Requests')
plt.ylim(bottom=-0.1)
plt.xlim(left=0)
plt.savefig('total_responses.png')

####################################################
# Create total requests against success ratio plot #
####################################################
# Count number of failed requests
failure_counts = failure_df.groupby(by=['start_time'])['exception'].count().reindex(timesteps, fill_value=0).reset_index().rename({'exception': 'num_errors'}, axis='columns')

# Compute success ratio of the requests.
combined = pd.merge(aggregated_counts, failure_counts, on='start_time')
combined['success_ratio'] = combined.apply(lambda row: 1 - row['num_errors'] / row['num_requests'], axis=1)
fig, ax = plt.subplots(figsize=(15, 7.5))
sns.lineplot(legend=False, x='num_requests', y='success_ratio', data=combined, ax=ax)
plt.xlabel('Number of Requests')
plt.ylabel('Percentage of Successful Requests')
plt.ylim((-0.1, 1.1))
plt.legend(loc='lower left', labels=labels)
plt.savefig('success_ratio.png')


####################################################
# Create total requests against response time plot #
####################################################
fig, ax = plt.subplots(figsize=(15, 7.5))
sns.lineplot(ax=ax, x='num_requests', y='response_time', data=pd.merge(success_df, aggregated_counts, on='start_time'))
plt.xlabel('Number of Requests')
plt.ylabel('Response Time (ms)')
plt.ylim(bottom=-0.1)
plt.savefig('total_vs_response.png')
