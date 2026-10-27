#!/bin/bash

# Script stops on errors
set -e

echo "Building project with Maven..."
mvn clean compile
echo "Build successful."
echo ""

echo "========================================="
echo " RUNNING REQUIREMENT 5 (Same Process)"
echo "========================================="
mvn exec:java -Dexec.mainClass="com.assessment.player.main.SameProcessMain"
echo ""

echo "========================================="
echo " RUNNING REQUIREMENT 7 (Separate Processes)"
echo "========================================="

# Start Receiver in the background
mvn exec:java -Dexec.mainClass="com.assessment.player.main.SeparateProcessMain" -Dexec.args="server" &
SERVER_PID=$!

# Start Initiator in the foreground
mvn exec:java -Dexec.mainClass="com.assessment.player.main.SeparateProcessMain" -Dexec.args="client"

# Wait for background process to finish
wait $SERVER_PID

echo ""
echo "========================================="
echo " All tasks finalized gracefully."
echo "========================================="