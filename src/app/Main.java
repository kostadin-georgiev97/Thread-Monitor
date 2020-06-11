package app;
import controller.Controller;

public class Main {

	public static void main(String[] args) {
	    Thread threadMonitor = new Controller("Thread Monitor");
		threadMonitor.start();
	}

}
