import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDateTest {

    /**
     * Takes a MyDate thread, tries to kill it and checks if it is alive.
     */
    @Test
    void myThreadGrcefulInterrupt() {
        Thread tempThread = new Thread(new MyDate("TestThread"));
        tempThread.interrupt();

        assertFalse(tempThread.isAlive());
    }
}