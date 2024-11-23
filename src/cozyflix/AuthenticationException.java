package cozyflix;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {	}
	public AuthenticationException(String email, String password) {
		super(String.format("Failed to login with credentials:\n%s\n%s\n", email, password));
	}

}
