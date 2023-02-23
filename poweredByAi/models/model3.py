# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 3 : Predicts which issue will appear next to the current issue**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import pandas as pd
import numpy as np

# For model 3
from mlxtend.frequent_patterns import apriori
from mlxtend.frequent_patterns import association_rules


def model3(data):
    trouble_data = model3_data(data)
    model3_train(trouble_data)


def model3_data(data):
    # Filter for rows where "issues" == 1
    filtered_data = data[data["issues"] == 1].copy()

    # Create a pivot table to get all unique trouble codes for each car ID
    pivot_table = filtered_data.pivot_table(index=["vehicle_id"], values=["trouble_codes"],
                                            aggfunc=lambda x: tuple(np.unique(x)))

    # Convert the pivot table to a NumPy array
    trouble_data = pivot_table.to_numpy()

    # Print the list of arrays
    print(trouble_data)

    return trouble_data


def model3_train(trouble_data):
    # Convert the dataset into a pandas DataFrame
    dataset = pd.DataFrame(trouble_data)

    # Print the dataset
    print(dataset)

    # Transform the dataset into a one-hot encoded matrix
    onehot = pd.get_dummies(dataset.apply(pd.Series).stack()).sum(level=0)

    # Generate frequent item_sets using the Apriori algorithm
    frequent_item_sets = apriori(onehot, min_support=0.3, use_colnames=True)

    # Generate association rules from frequent itemsets
    rules = association_rules(frequent_item_sets, metric="lift", min_threshold=1)

    # Print the association rules
    print(rules)

    return rules

