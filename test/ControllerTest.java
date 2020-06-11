import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    View gui;
    Model model;

    /**
     * Creates a GUI object and Model object before every test.
     */
    @BeforeEach
    void setUp() {
        gui = new View();
        model = new Model();
    }

    /**
     * Starts a thread and checks whether the main thread array has increased in size.
     */
    @Test
    void startThreadTest() {
        int sizeBefore = model.getAllThreads().length;
        new Thread(new MyDate("MyDate")).start();
        int sizeAfter = model.getAllThreads().length;

        assertEquals(sizeAfter, sizeBefore + 1);
    }

    /**
     * Interrupts a thread and checks whether it's been interrupted.
     */
    @Test
    void interruptThreadTest() throws InterruptedException {
        Thread tempThread = new Thread(new MyDate("TestThread"));
        tempThread.start();
        tempThread.interrupt();
        Thread.sleep(1000);

        assertFalse(tempThread.isInterrupted());
    }

    /**
     * Takes a random current thread, tries to kill it and checks if it is alive.
     */
    @Test
    void randomThreadInterrupt() {
        Thread randomThread = Thread.currentThread();
        randomThread.interrupt();

        assertTrue(randomThread.isAlive());
    }
}