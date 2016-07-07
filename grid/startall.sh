#!/bin/bash

# . set_environment.sh

#export CLASSPATH=${PROJECT_HOME}/gemfire-server/target/gemfire-server-0.0.1-SNAPSHOT.jar:${PROJECT_HOME}/domain/target/domain-0.0.1-SNAPSHOT.jar

KAFKA_PRODUCER_JAR=../target/gemfire-2-kafka-0.0.1-SNAPSHOT.jar
cp $KAFKA_PRODUCER_JAR locator1/cluster_config/cluster/

KAFKA_JAR=/Users/wwilliams/.m2/repository/org/apache/kafka/kafka-clients/0.9.0.1/kafka-clients-0.9.0.1.jar
JSON_JAR=/Users/wwilliams/.m2/repository/org/json/json/20160212/json-20160212.jar
#GSON_JAR=../../iddd_tradercommon/lib/gson-2.1.jar

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
connect

start locator --name=locator1 --port=10334 --properties-file=config/locator.properties --load-cluster-configuration-from-dir=true --initial-heap=256m --max-heap=256m

start server --classpath=$KAFKA_JAR:$JSON_JAR:../$KAFKA_PRODUCER_JAR --name=server1 --server-port=0 --initial-heap=2g --max-heap=2g --properties-file=config/gemfire.properties
#start server --classpath=$JUNIT_JAR:$GSON_JAR --name=server2 --server-port=0 --initial-heap=2g --max-heap=2g --properties-file=config/gemfire.properties

#deploy ../target/gemfire-kafka-0.0.1-SNAPSHOT.jar
#deploy ../iddd_tradercommon/lib/junit-4.8.2.jar

list members;
list regions;
!
