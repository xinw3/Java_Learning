package threadSafe;

/**
 * using multiple locks to synchronize blocks in the application
 * 
 * @author xinwang
 *
 */
public class App {

	public static void main(String[] args) {
		new Worker().main();
	}

}
