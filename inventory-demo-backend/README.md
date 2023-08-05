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

## Design considerations

The application consists of three main layers that are also reflected in the package structure.

**Web:** Includes the rest endpoints, exception handling and security config. This is where all the web related stuff
should go.

**Service:** Contains all business logic and the domain model.

**Data:** Contains all logic related to database access.

Each layer has its own model classes. Manual mapping of object data is done in the service layer, because that's where
some values are computed.
The weblayer communicates to the outside world using dto's. This allows you to modify the internal models in the
service- and data layers without accidentally breaking your contract.
Mapping to the dto's in the weblayer is done using mapstruct. This prevents you from having to type all the trivial
mappings by hand.

## Future improvements

- The API is now using basic auth with an in memory user store. This should be replaced by something that can be used in
  production, like a user store on disk/ldap.
- You could also replace the current authentication setup with a token based variant (OAuth). The backend should then be
  configured as a resource server.
    - Then you'd probably also need to introduce
      a [BFF](https://aws.amazon.com/blogs/mobile/backends-for-frontends-pattern/#:~:text=According%20to%20Sam%20Newman%2C%20the,one%20general%2Dpurpose%20API%20backend.)
      to avoid storing tokens in the browser
- The application now uses an in-memory variant of h2. This should also be replaced with a real database
- Input validation could be improved in for updating the inventory. It now always accepts the new state regardless of
  current reservations
- The implementation for cleaning up the reservation is now based on a scheduler. This leads to a lot of queries to the
  database. If that causes a (performance) problem, another solution should be introduced. Probably one that is
  event-based.