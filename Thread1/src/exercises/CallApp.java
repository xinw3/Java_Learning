package exercises;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallApp {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
				
		System.out.println("Starting...");
		
		Future<?> future = executor.submit(new Callable<Void>() {
			public Void call() throws IOException {
				
				Random random = new Random();
				int duration = random.nextInt(4000);
				
				if (duration > 2000) {
					throw new IOException("Sleeping for too long");
				}
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Finished...");
				return null;
			}
				
			
		});
		
		executor.shutdown();
		//executor.awaitTermination(timeout, unit)
		try {
			System.out.println("Result is: " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			IOException ex = (IOException) e.getCause();
			System.out.println(ex.getMessage());
		}
	}

}
