JAVA Implementation for APRIORI Algorithm
===

APRIORI Algorithm is divided into 3 parts
===
1. Defining Minimum Support and Minimum Confidence.
2. Finding Frequent Itemset
   * Find items whose occurrence is more than the support specified
   * Finding item set i.e. combinations of items from items generated from previous iterations
3. Generating Association Rules.

DATASET
====
```
iPhone Samsung Lenevo
Nokia iPhone Samsung LG Lenevo
Nokia Samsung
iPhone Samsung Nokia
Lenevo
Samsung LG
Samsung Lenevo
```
Each line specifies a Transaction which is seperated by a space.

OUTPUT
===
```
No of Items = 5
No of Transactions = 7
Minimum Support = 2
Minimum Confidence = 0.45

'Items present in the Database'
------> iPhone
------> Samsung
------> Lenevo
------> Nokia
------> LG

TRANSACTION       ITEMSET
Transaction 1 = iPhone Samsung Lenevo
Transaction 2 = Nokia iPhone Samsung LG Lenevo
Transaction 3 = Nokia Samsung
Transaction 4 = iPhone Samsung Nokia
Transaction 5 = Lenevo
Transaction 6 = Samsung LG
Transaction 7 = Samsung Lenevo
```
