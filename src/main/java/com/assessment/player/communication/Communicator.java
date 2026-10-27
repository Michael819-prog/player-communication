package com.assessment.player.communication;

/**
 * For sending and receiving messages.
 * Allows the same Player class to operate both in the same process and across separate processes
 */
public interface Communicator extends AutoCloseable {
    void send(String message) throws Exception;
    String receive() throws Exception;
}
