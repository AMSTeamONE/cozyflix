package cozyflix;

import java.util.ArrayList;

public class Streamming {
	private ArrayList<Task> tasks;
	private ArrayList<String> history;
	private String email, password;
	private long executionTime;
	
	public void authenticate(String email, String password) {
		if (!this.email.equals(email) || !this.password.equals(password))
			throw new AuthenticationException(email, password);
	}
	
	public Streamming(String email, String password) {
		this.email = email;
		this.password = password;
		this.tasks = new ArrayList<Task>();
		this.history = new ArrayList<String>();
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
		Task next = getNextTask();
		while (next != null) {
			if (next instanceof WatchTask) {
				history.add(((WatchTask) next).getMediaName());
			}
			
			long executionStart = System.currentTimeMillis();
			
			Thread t = new Thread(next);
			t.start();
			t.join();
			
			if (!(next instanceof AuthTask) ) {
				long delta = System.currentTimeMillis() - executionStart;
				executionTime += delta;
			}
			
			next = getNextTask(); 
		}
		
		// when it runs out or when space is pressed, call the task selection menu
		
		System.out.printf("Total time: %fh\n", (float) executionTime / 3600);
		
		System.out.println("Execution history:");
		for (String media : history) {
			System.out.printf("- %s\n", media);
		}
	}
}
