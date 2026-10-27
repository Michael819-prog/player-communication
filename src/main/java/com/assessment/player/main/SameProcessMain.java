package com.assessment.player.main;

import com.assessment.player.Player;
import com.assessment.player.communication.LocalCommunicator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Requirement 5.
 * 2 Players sharing LocalCommunicators, running them on separate threads within this 1 process.
 */
public class SameProcessMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Starting Mode: SAME PROCESS ---");

        BlockingQueue<String> queue1To2 = new ArrayBlockingQueue<>(10);
        BlockingQueue<String> queue2To1 = new ArrayBlockingQueue<>(10);

        LocalCommunicator comm1 = new LocalCommunicator(queue2To1, queue1To2);
        LocalCommunicator comm2 = new LocalCommunicator(queue1To2, queue2To1);

        Player initiator = new Player("Initiator", comm1, true);
        Player receiver = new Player("Receiver", comm2, false);

        Thread t1 = new Thread(initiator);
        Thread t2 = new Thread(receiver);

        t2.start(); // Receiver starts listening
        t1.start(); // Initiator starts sending

        t1.join();
        t2.join();

        System.out.println("--- Same Process Mode Finished ---");
    }
}