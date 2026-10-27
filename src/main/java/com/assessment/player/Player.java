package com.assessment.player;

import com.assessment.player.communication.Communicator;

/**
 * Participant in the message exchange.
 * Handles sending the initial message (initiator), receiving messages,
 * concatenating its sent counter to the reply, and enforcing 10-message stop condition.
 */
public class Player implements Runnable {
    private final String name;
    private final Communicator communicator;
    private final boolean isInitiator;
    private int sentCounter = 0;
    private int receivedCounter = 0;
    private static final int MAX_MESSAGES = 10;

    public Player(String name, Communicator communicator, boolean isInitiator) {
        this.name = name;
        this.communicator = communicator;
        this.isInitiator = isInitiator;
    }

    @Override
    public void run() {
        try {
            if (isInitiator) {
                sentCounter++;
                String initialMessage = "Hello from " + name;
                System.out.println(name + " sending: " + initialMessage);
                communicator.send(initialMessage);
            }

            while (true) {
                String receivedMsg = communicator.receive();
                if (receivedMsg == null) {
                    break;
                }
                receivedCounter++;
                System.out.println(name + " received: " + receivedMsg);

                // max reached
                if (isInitiator && receivedCounter >= MAX_MESSAGES) {
                    System.out.println(name + " successfully exchanged " + MAX_MESSAGES + " messages. Terminating gracefully.");
                    break;
                }

                sentCounter++;
                String reply = receivedMsg + " " + sentCounter;
                System.out.println(name + " sending: " + reply);
                communicator.send(reply);

                // max reached
                if (!isInitiator && sentCounter >= MAX_MESSAGES) {
                    System.out.println(name + " successfully exchanged " + MAX_MESSAGES + " messages. Terminating gracefully.");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(name + " encountered an error: " + e.getMessage());
        } finally {
            try {
                communicator.close();
            } catch (Exception ignored) {
            }
        }
    }
}