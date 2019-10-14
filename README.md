# Wallet

by Daniil Azarnov [![Build Status](https://travis-ci.org/DanilAzarnov/wallet.svg?branch=master)](https://travis-ci.org/DanilAzarnov/wallet)

## Introduction

Wallet is Simple RESTful API for money transfers between accounts.

## How to start

```
docker-compose up
```

## How to use

Create new account Oleg with 1000000 money:
```
curl -d "@oleg.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/account/create
```
When oleg.json:
```json
{
  "name": "Oleg",
  "amount": 1000000
}
```
Create new account German:
```
curl -d "@german.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/account/create
```
When german.json:
```json
{
  "name": "German"
}
```
And the last command Oleg sending 'sotku' to German.
```
curl -d "@operation.json" -X POST -H "Content-Type: application/json" http://localhost:8080/api/operation/send
```
When operation.json:
```json
{
  "amount": 100,
  "fromAccount": {
    "id": 1,
    "name": "oleg"
  },
  "toAccount": {
    "id": 2,
    "name": "german"
  }
}
```





