import sys

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

# Load data
success_df = pd.read_csv(sys.argv[1], sep=';')
failure_df = pd.read_csv(sys.argv[2], sep=';')

success_df['start_time'] = success_df['start_time'] - success_df['start_time'].min()
failure_df['start_time'] = failure_df['start_time'] - failure_df['start_time'].min()
success_df['endpoint'] = success_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))
failure_df['endpoint'] = failure_df['endpoint'].apply(lambda value: '/'.join(value.split('/')[3:]))

# Create response time plot
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

# Create total requests plot
max_timestamp = success_df['start_time'].max()
individual_counts = success_df.groupby(by=['endpoint', 'start_time'])['response_time'].count()
individual_counts = individual_counts.reindex(
    pd.MultiIndex.from_product([individual_counts.index.levels[0], range(max_timestamp)],
                               names=['endpoint', 'start_time']),
    fill_value=0)
individual_counts = individual_counts.reset_index()
aggregated_counts = success_df.groupby(by=['start_time'])['response_time'].count()
aggregated_counts = aggregated_counts.reindex(range(max_timestamp), fill_value=0).reset_index()
aggregated_counts['endpoint'] = 'combined'
counts = pd.concat([individual_counts, aggregated_counts], sort=False)
max_count = counts['response_time'].max()
fig, ax = plt.subplots(figsize=(15, 7.5))
sns.lineplot(data=counts, hue='endpoint', ax=ax, x='start_time', y='response_time')
plt.xlabel('Time (s)')
plt.ylabel('Number of Requests')
plt.ylim((0, max_count * 1.1))
plt.show()
