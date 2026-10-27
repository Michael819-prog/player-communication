# 360T Player Communication Task

## Overview
This project implements a message-passing system between two `Player` instances, adhering to strict stop-conditions (10 messages sent/received). No external frameworks used.

## Design Decisions
`Player` class relies on a `Communicator` interface. This allows the same `Player` logic to be used in different network topologies:
1. **`LocalCommunicator`**: Uses thread-safe `BlockingQueue` implementations for intra-process communication (Requirement 5).
2. **`SocketCommunicator`**: Wraps standard Java TCP Sockets for inter-process communication (Requirement 7).

## Prerequisites
* Java 8 installed and set in `$PATH`.
* Maven 3.6+ installed.

## How to Build and Run
A shell script is provided to compile the project and execute both required scenarios sequentially.

1. Ensure the script is executable:
   ```bash
   chmod +x start.sh

2. Run the script:
   ```bash
   ./start.sh

## Manual Execution
Running requirements manually using Maven:

Requirement 7 (Separate Processes):
Two terminal windows are required.
1. Terminal 1 (Receiver/Server):
   ```bash
   mvn exec:java -Dexec.mainClass="com.assessment.player.main.SeparateProcessMain" -Dexec.args="server"

2. Terminal 2 (Initiator/Client):
   ```bash
   mvn exec:java -Dexec.mainClass="com.assessment.player.main.SeparateProcessMain" -Dexec.args="client"

Requirement 5 (Same Process):
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.assessment.player.main.SameProcessMain"