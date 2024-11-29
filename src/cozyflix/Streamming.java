package cozyflix;

import java.util.ArrayList;

import cozyflix.WatchTask.MediaType;

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
		
		exec:
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
			
			boolean autoplay = false;
			short episodes = 0;
			MediaType mt = MediaType.MOVIE;
			String media = "";
			long duration = 120 * 60;
			
			System.out.print("----------\n1) Proceed\n2) Add task\n3) Quit\n>> ");
			switch (UserInput.askInt()) {
			case 1:
				break;
			case 2: {
				System.out.print("----------\n1) Authentication\n2) Add to watchlist\n3) Download\n>> ");
				switch (UserInput.askInt()) {
				case 1: {
					addTask(new AuthTask(this));
					break;
				}
				case 2:
					System.out.print("Media type:\n1) Movie\n2) Series\n>> ");
					switch (UserInput.askInt()) {
					case 1:
						break;
					case 2: 
						mt = MediaType.SERIES;
						autoplay = true;
						episodes = 3;
						duration = 30;
						break;
					default: 
						System.out.println("Invalid media choice. Defaulting to movie.");
					};
					
					media = UserInput.ask("Media");
					
					addTask(new WatchTask(media, duration, autoplay, mt, episodes));
					
					break;
				case 3:
					media = UserInput.ask("Media");
					addTask(new DownloadTask(media));
					break;
					
				default: 
					System.out.println("Invalid task choice");
					break;
				}
				break;
			}
			case 3: {
				break exec;
			}
			default: {
				System.out.println("Invalid choice. Proceeding execution");
				break;
			}
			}
			
			next = getNextTask();
		}
		
		System.out.printf("Total time: %.2fh\n", (float) executionTime / 3600);
		
		System.out.println("Execution history:");
		for (String media : history) {
			System.out.printf("- %s\n", media);
		}
	}
}
