package cloudera;

public class Account {

	private int balance = 0;
	private String name = "";
	public Account(String n) {
		name = n;
	}
	
	public void deposit(int amount) {
		balance += amount;
//		if (balance > 1000000) {
//			System.out.println("Hi " + name + ", Your balance cannot be over 1000000");
//			balance -= amount;
//			return;
//		}
	}
	
	public void withdraw(int amount) {
		balance -= amount;
//		if (balance < 0) {
//			System.out.println("Hi " + name + ", Your balnace is not enough.");
//			balance += amount;
//			return;
//		}
	}
	
	public void transfer(Account acc1, Account acc2, int amount) {
		acc1.withdraw(amount);
		acc2.deposit(amount);
	}
	
	public int getBalance() {
		return balance;
	}
	public String getName() {
		return name;
	}

}
