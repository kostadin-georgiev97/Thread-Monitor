package model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyDate extends Thread {
	
	DateFormat df;
	Calendar calobj;
	boolean runFlag;
	
	public MyDate(String name) {
		super(name);
		df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	}

	@Override
	public void run() {
		runFlag = true;
		while (runFlag) {
			calobj = Calendar.getInstance();
			System.out.println("The date & time in thread " + Thread.currentThread().getName() + " is: " + df.format(calobj.getTime()));
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				runFlag = false;
				System.out.println("Thread interrupted.");
            }
		}
	}
}
