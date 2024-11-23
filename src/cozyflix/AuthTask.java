package cozyflix;

import java.util.Scanner;

public class AuthTask extends Task {

	private Streamming account;
	
	public AuthTask(Streamming account) {
		super();
		this.setPriority(1);
		this.account = account;
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("E-mail: ");
		String email = scanner.nextLine();
		
		System.out.print("Senha: ");
		String password = scanner.nextLine();
		
		try {
			this.account.authenticate(email, password);
		} catch (AuthenticationException e) {
			System.err.println(e.getMessage());
		} finally {
			scanner.close();
		}
	}

}
