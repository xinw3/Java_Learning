package exercises;

import java.util.Random;

public class InterruptableApp {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Staring...");
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				Random ran = new Random();
				
				for (int i = 0; i < 1e8; i++) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
					}
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Math.sin(ran.nextDouble());
				}
			}
		});
		t1.start();
		
		Thread.sleep(500);
		
		t1.interrupt();
		
		t1.join();
		

	}

}
