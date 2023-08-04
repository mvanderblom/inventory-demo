# Inventory demo backend

A backend project for an inventory management system.

## Running it locally

Run the application locally by executing the following command from this directory
`.\gradlew bootRun`

## Exploring the api

Explore the api by visiting the swagger ui at: http://localhost:8080

The open api spec can be found at http://localhost:8080/api-docs

## Authentication

The swaggerdocs and open api spec are accessible by anyone.

The api is secured using basic authentication

The system knows two users (with corresponding passwords):
- user:user
- admin:admin

All GET endpoints can be consumed by both "user" and "admin".

All endpoints that mutate state can be consumed by the user "admin" only

## Running tests

Run the tests by executing the following command from this directory
`.\gradlew test`