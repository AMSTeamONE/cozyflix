package cozyflix;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class UserInput {
	
	private static final UserInput INSTANCE = new UserInput();

	private static volatile boolean callTask = false;
	
    public static boolean isWPressed() {
        synchronized (UserInput.class) {
            return callTask;
        }
    }
	
	private UserInput() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (UserInput.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            callTask = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            callTask = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
	}

	public static UserInput getInstance() {
		return INSTANCE;
	}
}
