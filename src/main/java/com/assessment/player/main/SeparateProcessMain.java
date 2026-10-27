package com.assessment.player.main;

import com.assessment.player.Player;
import com.assessment.player.communication.SocketCommunicator;

import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Requirement 7.
 * CLI argument to act as either the Server (Receiver) or Client (Initiator).
 */
public class SeparateProcessMain {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java SeparateProcessMain <server|client>");
            System.exit(1);
        }

        String mode = args[0].toLowerCase();

        // Java 8 compatible way to get the PID
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        try {
            if ("server".equals(mode)) {
                try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                    System.out.println("Receiver (Server) waiting for connection on port " + PORT + " (PID: " + pid + ")");
                    Socket clientSocket = serverSocket.accept();
                    SocketCommunicator comm = new SocketCommunicator(clientSocket);
                    Player receiver = new Player("Receiver", comm, false);
                    receiver.run();
                }
            } else if ("client".equals(mode)) {
                Thread.sleep(1000);
                System.out.println("Initiator (Client) connecting to port " + PORT + " (PID: " + pid + ")");
                Socket socket = new Socket("localhost", PORT);
                SocketCommunicator comm = new SocketCommunicator(socket);
                Player initiator = new Player("Initiator", comm, true);
                initiator.run();
            }
        } catch (Exception e) {
            System.err.println("Network error: " + e.getMessage());
        }
    }
}