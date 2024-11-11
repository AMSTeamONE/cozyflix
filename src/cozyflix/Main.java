package cozyflix;

public class Main {
	public static void main(String[] args) {
		Streamming minhaConta = new Streamming(1);
		minhaConta.addTask(new WatchTask("Akira", (long) 2 * 3600, false, WatchTask.MediaType.MOVIE, (short) 0));
		minhaConta.addTask(new WatchTask("Arcane", (long) 40 * 60, true, WatchTask.MediaType.SERIES, (short) 3));
		
		try {
			minhaConta.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
