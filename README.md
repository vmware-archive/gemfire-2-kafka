<h1> VMware has ended active development of this project, this repository will no longer be updated.</h1><br># gemfire-2-kafka
Sample that writes to Kafka from GemFire using GemFire's Async Eventing


https://www.mapr.com/blog/getting-started-sample-programs-apache-kafka-09

Step 1: Download and Install
http://kafka.apache.org/downloads.html

Step 2: Start Zookeeper
$ bin/zookeeper-server-start.sh config/zookeeper.properties &

Step 3: Start Kafka Server
$ bin/kafka-server-start.sh config/server.properties

Step 4: Create the topic for the example programs
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 \
--replication-factor 1 --partitions 1 --topic quotes

Step5: Maven package the project into a jar
$ mvn package
- Must be packaged because the GemFire startup script will copy it into the deployment directory so that the listeners will be available to GemFire

Step6: Start GemFire cluster
grid $ . startall.sh

Notes on what startall.sh does:
- copies project client jar into cluster config directory AND puts both kafka jar and the project into the classpath
- references kafka properties and loads them into the kafka client. In this project, the kafka client is the AyncEventListener
- PDX persistence must be turned on for serialization

What puzzles me:
- Kafka is advertised to continue throughput even with slow consumers. However, this is not my experience. I put a slow consumer to print to console every record and the producer slowed by about 1/3. TODO
- Can I control the partitioning to align with GemFire’s partitions, e.g.?
- Design for recoverability/ idempotency since kafka is not transactional
