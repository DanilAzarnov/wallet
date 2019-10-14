# wallet

Simple RESTful API (including data model and the backing implementation) for money transfers between accounts.



curl -d "@oleg.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/account/create

curl -d "@german.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/account/create

curl -d "@operation.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/operation/send
