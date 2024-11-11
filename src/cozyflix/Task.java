package cozyflix;

public abstract class Task implements Runnable {
	private int priority;
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}
}
