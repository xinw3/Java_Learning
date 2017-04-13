package cloudera;

public class AccountApp {

	public static void main(String[] args) throws InterruptedException {
		Account acc = new Account("Xin");
		Account acc2 = new Account("Wang");
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					acc.deposit(1000);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					acc.withdraw(1000);
				}
			}
		});
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		System.out.println("Account balance is: " + acc.getBalance());
	}
}
	
	/*
	 * Simple sequential version
	 */
//	public static void main(String[] args) {
//		Account acc1 = new Account("Xin");
//		Account acc2 = new Account("Wang");
//		acc1.deposit(10000000);
//		acc2.deposit(1000);
//		
//		acc1.withdraw(200);
//		acc2.withdraw(1001);
//		
//		System.out.println("Account 1 balance: " + acc1.getBalance());
//		System.out.println("Account 2 balance: " + acc2.getBalance());
//	}
//}
