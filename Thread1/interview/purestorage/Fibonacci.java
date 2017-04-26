package purestorage;

public class Fibonacci {

	public int fib_dynamic(int n) {
		int[] fib = new int[n + 1];
		fib[0] = 0;
		fib[1] = 1;
		for (int i = 2; i <= n; i++) {
			fib[i] = fib[i - 1] + fib[i - 2];			
		}
		return fib[n];
	}
	
	/* version 1: recursion
	 * if we consider the function call stack
	 * we need O(n) space. Otherwise O(1)
	 */
	public int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fibonacci f = new Fibonacci();
		System.out.println(f.fib(9));
		System.out.println(f.fib_dynamic(9));
	}

}
