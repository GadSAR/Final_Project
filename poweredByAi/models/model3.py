# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 3 : Predicts which issue will appear next to the current issue**"""
import itertools

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import pandas as pd
import numpy as np


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
    frequent_item_sets = apriori(onehot, min_support=0.3)

    # Generate association rules from frequent itemsets
    rules = association_rules(frequent_item_sets, min_confidence=1)

    # Print the association rules
    print(rules)

    return rules


def apriori(transactions, min_support):
    itemsets = {}
    support = {}

    # Generate the frequent 1-itemsets
    for transaction in transactions:
        for item in transaction:
            if item not in itemsets:
                itemsets[item] = 1
            else:
                itemsets[item] += 1

    # Filter the frequent 1-itemsets based on min_support
    num_transactions = len(transactions)
    for item in itemsets.copy():
        if itemsets[item] / num_transactions < min_support:
            del itemsets[item]

    # Generate the frequent k-itemsets (k > 1)
    k = 2
    while itemsets:
        frequent_k_itemsets = {}

        # Generate candidate k-itemsets
        candidate_itemsets = set()
        for itemset in itemsets:
            for item in itemsets:
                if set(item) > set(itemset) and len(set(item).union(set(itemset))) == k:
                    # perform some operation
                    candidate_itemsets.add(item.union(itemset))

        # Count the support of candidate k-itemsets
        for transaction in transactions:
            for candidate in candidate_itemsets:
                if candidate.issubset(transaction):
                    if candidate not in frequent_k_itemsets:
                        frequent_k_itemsets[candidate] = 1
                    else:
                        frequent_k_itemsets[candidate] += 1

        # Filter the frequent k-itemsets based on min_support
        itemsets = {}
        for itemset in frequent_k_itemsets:
            if frequent_k_itemsets[itemset] / num_transactions >= min_support:
                itemsets[itemset] = frequent_k_itemsets[itemset]
                support[itemset] = frequent_k_itemsets[itemset] / num_transactions

        k += 1

    return {"itemsets": itemsets, "support": support}


def association_rules(itemsets, min_confidence):
    rules = []

    for itemset in itemsets["itemsets"]:
        for i in range(1, len(itemset)):
            antecedents = itertools.combinations(itemset, i)
            for antecedent in antecedents:
                antecedent = frozenset(antecedent)
                consequent = itemset.difference(antecedent)
                if antecedent in itemsets["support"] and consequent in itemsets["support"]:
                    confidence = itemsets["support"][itemset] / itemsets["support"][antecedent]
                    if confidence >= min_confidence:
                        rules.append((antecedent, consequent, confidence))

    return rules
