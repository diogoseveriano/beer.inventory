# Inventory Manager for Beer Production / Beer Brands


Simple application to handle and control the inventory for a beer production factory.

The purpose of this is to be used as a real-time or intermittent inventory control.

Some rules only apply to the Portuguese Law System, those will be identified and listed below once they are created.
For those, we'll have a flag that can be disabled on the application properties level to disable them.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.