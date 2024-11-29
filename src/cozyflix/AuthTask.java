package cozyflix;

public class AuthTask extends Task {

	private Streamming account;
	
	public AuthTask(Streamming account) {
		super();
		this.setPriority(1);
		this.account = account;
	}

	@Override
	public void run() {
		System.out.println("Login:");
		String email = UserInput.askEmail();
		String password = UserInput.askPassword();
		
		try {
			this.account.authenticate(email, password);
		} catch (AuthenticationException e) {
			System.err.println(e.getMessage());
		}
	}

}
