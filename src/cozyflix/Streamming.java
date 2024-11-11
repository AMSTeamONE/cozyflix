package cozyflix;

import java.util.ArrayList;
import java.util.Iterator;

public class Streamming {
	private ArrayList<Task> tasks;
	private int allowedScreens;
	
	public Streamming(int allowedScreens) {
		this.allowedScreens = allowedScreens;
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
		Task next = getNextTask();
		while (next != null) {
			Thread t = new Thread(next);
			t.start();
			t.join();
			next = getNextTask();
		}
	}
}
