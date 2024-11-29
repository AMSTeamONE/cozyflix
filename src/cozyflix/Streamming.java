package cozyflix;

import java.util.ArrayList;

import cozyflix.WatchTask.MediaType;

public class Streamming {

    // Method to add tasks at specific positions
    public void addTaskAt(Task task, String position) {
        switch (position.toLowerCase()) {
            case "start":
                this.tasks.add(0, task);
                break;
            case "middle":
                int middle = tasks.size() / 2;
                this.tasks.add(middle, task);
                break;
            case "end":
                this.tasks.add(task);
                break;
            default:
                System.out.println("Invalid position! Task added to the end by default.");
                this.tasks.add(task);
        }
    }

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
			
			boolean autoplay;
			MediaType mt = MediaType.MOVIE;
			
			System.out.print("----------\n1) Proceed\n2) Add task\n3)Quit\n>> ");
			switch (UserInput.askInt()) {
			case 1:
				continue;
			case 2: {
				System.out.print("----------\n1) Authentication\n2) Add to watchlist\n3) Download\n>> ");
				switch (UserInput.askInt()) {
				case 1: {
					addTask(new AuthTask(this));
					break;
				}
				case 2: {
					System.out.print("Autoplay [y/N]: ");
					autoplay = UserInput.askBoolean();
					
					System.out.print("Media type: 1) Movie\n2) Series\n>> ");
					switch (UserInput.askInt()) {
					case 2: {
						mt = MediaType.SERIES;
						break;
					}
					default: {
						System.out.println("Invalid media choice. Defaulting to movie.");
					}
					};
					
					Task task = new WatchTask(UserInput.ask("Media"), (long) UserInput.askInt("Duration"), autoplay, mt);
					
					System.out.print("Would you like to add a custom priority? [y/N] ");
					if (UserInput.askBoolean())
						task.setPriority(UserInput.askInt("Custom priority: "));
					
					addTask(task);
					break;
				}
				default: {
					System.out.println("Invalid task choice");
					break;
				}
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
			if (next == null || UserInput.callMenuActive()) {
				
			}
		}
		
		System.out.printf("Total time: %.2fh\n", (float) executionTime / 3600);
		
		System.out.println("Execution history:");
		for (String media : history) {
			System.out.printf("- %s\n", media);
		}
	}
}
