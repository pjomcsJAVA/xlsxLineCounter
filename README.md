# xlsxLineCounter

This little program counts all lines in all files with xlsx extension in a folder. This program assumes that all data is on the first sheet and
 that all sheets can have a header line which is not considered as a line depending on a boolean (XLS_HAS_HEADER_IN_EACH_SHEET).
 
 FOLDER_TO_CHECK_FOR_FILES -> folder to check for xlsx files (or another extension, but has to be an excel file)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvn quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `xlsx-line-counter-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/xlsx-line-counter-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/xlsx-line-counter-1.0.0-SNAPSHOT-runner`
