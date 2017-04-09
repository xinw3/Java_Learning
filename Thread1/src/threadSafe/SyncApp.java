package threadSafe;
/**
 * This program shows the problem of interleaving of
 * multiple threads(race condition) and how to fix it
 * using the keyword synchronized
 * 
 * @author xinwang
 *
 */
public class SyncApp {

	// don't need to declare it as volatile
	// because synchronized will handle it itself.
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args) {
		SyncApp app = new SyncApp();
		app.doWork();
	}
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					increment();
					// count = count + 1
					// three step: read count, increment, and store it back.
				}
			}
		});
		
		t1.start();
		t2.start();
		/**
		 * we need to wait for the thread to finish
		 * or else the output is wrong.
		 */
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Count is " + count);
	}

}
