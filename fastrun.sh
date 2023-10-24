#!/bin/sh

chmod +x gradlew
./gradlew clean build
java -jar build/libs/dango-0.0.1-SNAPSHOT.jar
