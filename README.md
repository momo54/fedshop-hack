# Cost Hackaton on Federated Query Engines

https://github.com/MaastrichtU-IDS/federatedQueryKG

FedShop Use-case: https://github.com/MaastrichtU-IDS/federatedQueryKG/blob/main/usecaseFedShop.md

Be sure having a Virtuoso endpoint running with batch_4 data (100 virtual endpoint) taken from
https://github.com/MaastrichtU-IDS/federatedQueryKG/blob/main/usecaseFedShop.md

To execute:
mvn compile exec:java -Dexec.args="fedx_batch_0.conf queries/q04.rq"
