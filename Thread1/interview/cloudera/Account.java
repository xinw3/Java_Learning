package cloudera;

public class Account {

	private int balance = 0;
	
	public void deposit(int amount) {
		if (balance > 1000000) {
			System.out.println("Your balance cannot be over 1000000");
			return;
		}
		balance += amount;
	}
	
	public void withdraw(int amount) {
		if (balance < 0) {
			System.out.println("You hava no balance in your account.");
			return;
		}
		balance -= amount;
	}
	
	public void transfer(Account acc1, Account acc2, int amount) {
		acc1.withdraw(amount);
		acc2.deposit(amount);
	}
	
	public int getBalance() {
		return balance;
	}

}
