package cozyflix;

import java.util.ArrayList;
import java.util.Iterator;

public class Streamming {
	private ArrayList<Task> tasks;
	private int allowedScreens;
	private String email, password;
	
	public void authenticate(String email, String password) {
		if (!this.email.equals(email) || !this.password.equals(password))
			throw new AuthenticationException(email, password);
	}
	
	public Streamming(int allowedScreens, String email, String password) {
		this.allowedScreens = allowedScreens;
		this.email = email;
		this.password = password;
		this.tasks = new ArrayList<Task>();
	}

	public void addTask(Task task) {
		this.tasks.addLast(task);
	}
	
	private Task getNextTask() {
		if (tasks.size() == 0) {
			return null;
		}
		
		int nextIndex = 0;
		
		for (int i = 1; i < tasks.size(); i++) {
			if (tasks.get(nextIndex).getPriority() < tasks.get(i).getPriority())
				nextIndex = i;
		}
		
		return tasks.remove(nextIndex);
	}
	
	public void start() throws InterruptedException {
		long start = System.currentTimeMillis();
		
		Task next = getNextTask();
		while (next != null) {
			Thread t = new Thread(next);
			t.start();
			t.join();
			next = getNextTask();
		}
		
		long delta = System.currentTimeMillis() - start;
		System.out.printf("Total time: %fh\n", (float) delta / 1000);
	}
}
