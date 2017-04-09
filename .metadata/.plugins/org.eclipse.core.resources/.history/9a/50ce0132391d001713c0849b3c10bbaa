package waysOfThreads;
/*
 * One way to use thread in java
 * -- extends thread class
 */
class Runner extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Hello " + i);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
public class App {

	public static void main(String[] args) {
		// It's important not to call run().
		// If call run, it excutes the run method in the current thread.
		// If call start, it starts a new thread and calls the run method.
		Runner runner1 = new Runner();
		runner1.start();
		
		Runner runner2 = new Runner();
		runner2.start();

	}

}
