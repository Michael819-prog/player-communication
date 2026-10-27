package com.assessment.player.communication;

import java.util.concurrent.BlockingQueue;

/**
 * Thread-safe BlockingQueues to pass messages securely between 2 Player threads.
 */
public class LocalCommunicator implements Communicator {
    private final BlockingQueue<String> incoming;
    private final BlockingQueue<String> outgoing;

    public LocalCommunicator(BlockingQueue<String> incoming, BlockingQueue<String> outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    @Override
    public void send(String message) throws InterruptedException {
        outgoing.put(message);
    }

    @Override
    public String receive() throws InterruptedException {
        return incoming.take();
    }

    @Override
    public void close() {
        // no need for cleaning
    }
}