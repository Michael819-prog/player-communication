package com.assessment.player.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Network Sockets to transmit messages over a TCP stream
 */
public class SocketCommunicator implements Communicator {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public SocketCommunicator(Socket socket) throws Exception {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void send(String message) {
        writer.println(message);
    }

    @Override
    public String receive() throws Exception {
        return reader.readLine();
    }

    @Override
    public void close() throws Exception {
        socket.close();
        reader.close();
        writer.close();
    }
}