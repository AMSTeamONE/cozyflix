package cozyflix;

import cozyflix.WatchTask.MediaType;

public class Main {
	public static void main(String[] args) {
		Streamming minhaConta = new Streamming(1, "jorge.terence@outlook.com", "nothing");
		minhaConta.addTask(new WatchTask("Akira", (long) 60 * 60 * 2, false, MediaType.MOVIE, (short) 0));
		minhaConta.addTask(new WatchTask("Arcane", (long) 40 * 60, true, MediaType.SERIES, (short) 3));
		minhaConta.addTask(new AuthTask(minhaConta));
		
		try {
			minhaConta.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
