package cozyflix;

public class WatchTask extends Task {

	public static enum MediaType {
		SERIES, MOVIE
	}

	private String mediaName;
	private long duration;
	private short episodes;
	private boolean autoplay;
	private MediaType type;

	public WatchTask(String mediaName, long duration, boolean autoplay, MediaType type, short episodes) {
		super();
		this.mediaName = mediaName;
		this.duration = duration;
		this.episodes = episodes;
		this.autoplay = autoplay;
		this.type = type;
	}
	
	public String getMediaName() {
		return mediaName;
	}

	@Override
	public void run() {
		switch (this.type) {
		case MediaType.MOVIE: {
			System.out.printf("Watching %s for %d minutes\n", this.mediaName, this.duration / 60);

			try {
				Thread.sleep(this.duration);
			} catch (InterruptedException e) {
				System.err.println("The internet went out");
			}

			break;
		}
		case MediaType.SERIES: {
			if (this.autoplay) {
				for (int i = 1; i <= episodes; i++) {
					System.out.printf("Watching %s episode %d for %d minutes\n", this.mediaName, i,
							Math.round(this.duration / 60));
					try {
						Thread.sleep(duration);
					} catch (InterruptedException e) {
						System.err.println("The internet went out");
					}
				}
			} else {
				System.out.printf("Watch %s episode %d for %d minutes\n", this.mediaName, 1,
						Math.round(this.duration / 60));
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					System.err.println("The internet went out");
				}
			}
		}
		}
	}

}
