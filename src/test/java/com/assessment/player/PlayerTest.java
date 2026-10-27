package com.assessment.player;

import com.assessment.player.communication.Communicator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the Player without needing actual threads or network sockets.
 */
class PlayerTest {

    static class StubCommunicator implements Communicator {
        List<String> sentMessages = new ArrayList<>();
        int receiveCallCount = 0;

        @Override
        public void send(String message) {
            sentMessages.add(message);
        }

        @Override
        public String receive() {
            receiveCallCount++;
            return "MockIncomingMessage"; // Receiving a message from the other player
        }

        @Override
        public void close() {
        }
    }

    @Test
    void testInitiatorStopsAfterSending10Messages() {
        StubCommunicator comm = new StubCommunicator();
        Player initiator = new Player("TestInitiator", comm, true);

        initiator.run();

        assertEquals(10, comm.sentMessages.size(), "Initiator should send exactly 10 messages before terminating.");
        assertTrue(comm.sentMessages.get(0).startsWith("Hello from TestInitiator"), "First message should be the initiator greeting.");
        assertTrue(comm.sentMessages.get(9).contains("10"), "Final message should contain the final counter.");
    }

    @Test
    void testReceiverStopsAfterSending10Messages() {
        StubCommunicator comm = new StubCommunicator();
        Player receiver = new Player("TestReceiver", comm, false);

        receiver.run();

        assertEquals(10, comm.sentMessages.size(), "Receiver should reply exactly 10 times before terminating.");
        assertTrue(comm.sentMessages.get(0).equals("MockIncomingMessage 1"), "Receiver should correctly append its counter to the incoming message.");
    }
}