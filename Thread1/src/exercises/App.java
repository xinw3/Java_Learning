package exercises;

public class App {

	public static void main(String[] args) throws InterruptedException {
		final DeadlockRunner runner = new DeadlockRunner();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				runner.firstThread();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				runner.secondThread();
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		runner.finished();
	}

}
