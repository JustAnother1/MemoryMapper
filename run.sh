#!/bin/bash
mvn verify
java -jar target/MemoryMapper-0.0.1-SNAPSHOT.jar -v -v
echo "Done !"
