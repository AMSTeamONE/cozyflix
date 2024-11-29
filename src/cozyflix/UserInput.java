package cozyflix;

import java.util.Scanner;

public final class UserInput {
	private UserInput() {}
	
	private static volatile boolean callTask = false;
	
	private static final Scanner scanner = new Scanner(System.in); 
	
    public static boolean callMenuActive() {
        synchronized (UserInput.class) {
            return callTask;
        }
    }
    
    public static String askEmail() {
    	System.out.print("E-mail: ");
    	return scanner.nextLine();
    }
    
    public static String askPassword() {
    	System.out.print("Password: ");
    	return scanner.nextLine();
    }
    
    public static String ask(String field) {
    	System.out.printf("%s: ", field);
    	return scanner.nextLine();
    }
    
    public static int askInt() {
    	return scanner.nextInt();
    }
    
    public static int askInt(String field) {
    	System.out.printf("%s: ", field);
    	return scanner.nextInt();
    }
    
    public static boolean askBoolean(String field) {
    	System.out.printf("%s: ", field);
    	return scanner.nextLine().toLowerCase().equals("y");
    }
    
    public static void close() {
    	scanner.close();
    }
}
