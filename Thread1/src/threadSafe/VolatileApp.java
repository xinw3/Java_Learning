package threadSafe;

import java.util.Scanner;

/**
 * This program shows the problem of thread caching items
 * and how to fix it.
 * @author xinwang
 *
 */
/*
 * How to terminate the threads? One way is to use threads interruptions.
 * Some times it won't work because the thread will copy its own variables(caching) and keep
 * the variable until the end of the thread.
 * So we need to use volatile keyword to prevent thread caching variables.
 * And the code is guarantee to work.
 */
class Processor extends Thread {
	private volatile boolean running = true;
	public void run() {
		while (running) {
			System.out.println("Hello!");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void shutdown() {
		running = false;
	}
}
public class VolatileApp {

	public static void main(String[] args) {
		Processor proc1 = new Processor();
		proc1.start();
		
		/*
		 * Scanner is used for parsing tokens from stream
		 * BufferedReader just reads the stream and doesn't do
		 * any special parsing.
		 */
		System.out.println("Press return to stop...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
		proc1.shutdown();

	}

}
