#!/bin/sh

chmod +x gradlew
./gradlew clean
./gradlew build -x test
java -jar build/libs/dango-0.0.1-SNAPSHOT.jar
