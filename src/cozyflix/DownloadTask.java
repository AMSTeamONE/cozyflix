package cozyflix;

public class DownloadTask extends Task {

	private String media;
	
	public DownloadTask(String media) {
		this.media = media;
	}

	@Override
	public void run() {
		System.out.println("Downloading: " + media);
		for (int i = 0; i <= 100; i += 10) {
			System.out.printf("Dowloading: %d%%\n", i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Failed to download");
				return;
			}
		}
		System.out.println("Download complete");
	}

}
