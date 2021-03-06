package cloudera;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

	private int balance;
	private String name;
	private Lock lock;
	private Condition hasBalance;
	public Account(String n) {
		name = n;
		balance = 0;
		lock = new ReentrantLock();
		hasBalance = lock.newCondition();
	}
	
	// remember to unlock before return 
	// or else there would
	// be threads hang up.
	public void deposit(int amount) {
		lock.lock();
		try {
			balance += amount;
			if (balance > 1000000) {
				System.out.println("balance cannot be over 1000000");
				balance -= amount;
				lock.unlock();
				return;
			}
			hasBalance.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void withdraw(int amount) throws InterruptedException {
		lock.lock();
		try {
			if (balance < amount) {
				hasBalance.await();
			}
			balance -= amount;
			
			if (balance < 0) {
				System.out.println("balnace is not enough.");
				balance += amount;
				lock.unlock();
				return;
			}
		} finally {
			lock.unlock();
		}
		
	}
	
	public void transfer(Account acc, int amount) throws InterruptedException {
		this.withdraw(amount);
		acc.deposit(amount);
	}
	
	public int getBalance() {
		return balance;
	}
	public String getName() {
		return name;
	}

}
