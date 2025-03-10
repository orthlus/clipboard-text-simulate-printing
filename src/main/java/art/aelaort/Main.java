package art.aelaort;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
	private static final Map<Character, Integer> SPECIAL_CHARACTERS = new HashMap<>();

	static {
		SPECIAL_CHARACTERS.put('*', KeyEvent.VK_8);        // * на клавише 8
		SPECIAL_CHARACTERS.put('?', KeyEvent.VK_SLASH);    // ? на клавише /
		SPECIAL_CHARACTERS.put('{', KeyEvent.VK_OPEN_BRACKET); // { на клавише [
		SPECIAL_CHARACTERS.put('}', KeyEvent.VK_CLOSE_BRACKET); // } на клавише ]
		SPECIAL_CHARACTERS.put('^', KeyEvent.VK_CIRCUMFLEX);   // ^ на клавише ^
		SPECIAL_CHARACTERS.put('-', KeyEvent.VK_MINUS);        // - на клавише -
		SPECIAL_CHARACTERS.put('_', KeyEvent.VK_MINUS);        // _ на клавише -
		SPECIAL_CHARACTERS.put('+', KeyEvent.VK_EQUALS);       // + на клавише =
		SPECIAL_CHARACTERS.put('=', KeyEvent.VK_EQUALS);       // = на клавише =
		SPECIAL_CHARACTERS.put('(', KeyEvent.VK_9);           // ( на клавише 9
		SPECIAL_CHARACTERS.put(')', KeyEvent.VK_0);           // ) на клавише 0
		SPECIAL_CHARACTERS.put('[', KeyEvent.VK_OPEN_BRACKET); // [ на клавише [
		SPECIAL_CHARACTERS.put(']', KeyEvent.VK_CLOSE_BRACKET); // ] на клавише ]
		SPECIAL_CHARACTERS.put('\\', KeyEvent.VK_BACK_SLASH);  // \ на клавише \
		SPECIAL_CHARACTERS.put('|', KeyEvent.VK_BACK_SLASH);   // | на клавише \
		SPECIAL_CHARACTERS.put(':', KeyEvent.VK_SEMICOLON);    // : на клавише ;
		SPECIAL_CHARACTERS.put(';', KeyEvent.VK_SEMICOLON);    // ; на клавише ;
		SPECIAL_CHARACTERS.put('"', KeyEvent.VK_QUOTE);        // " на клавише '
		SPECIAL_CHARACTERS.put('\'', KeyEvent.VK_QUOTE);       // ' на клавише '
		SPECIAL_CHARACTERS.put('<', KeyEvent.VK_COMMA);        // < на клавише ,
		SPECIAL_CHARACTERS.put('>', KeyEvent.VK_PERIOD);       // > на клавише .
		SPECIAL_CHARACTERS.put(',', KeyEvent.VK_COMMA);       // , на клавише ,
		SPECIAL_CHARACTERS.put('.', KeyEvent.VK_PERIOD);       // . на клавише .
		SPECIAL_CHARACTERS.put('/', KeyEvent.VK_SLASH);        // / на клавише /
	}

	public static void main(String[] args) {
		sleep(3);
//		setEnglish();
		press(getTextFromClipboard());
		pressEnter();
	}

	private static String getTextFromClipboard() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void sleep(int i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static void pressEnter() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	private static void press(String text) {
		try {
			Robot robot = new Robot();
			char[] charArray = text.toCharArray();
			for (char c : charArray) {
				try {
					boolean needShift = Character.isUpperCase(c) || isSpecialCharacter(c);

					if (c == '_') {
						needShift = true;
					} else if (c == '-') {
						needShift = false;
					}
					if (c == '{') {
						needShift = true;
					} else if (c == '[') {
						needShift = false;
					}

					if (needShift) {
						robot.keyPress(KeyEvent.VK_SHIFT);
					}

					int keyCode = getKeyCode(c);
					if (keyCode != KeyEvent.VK_UNDEFINED) {
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					}

					if (needShift) {
						robot.keyRelease(KeyEvent.VK_SHIFT);
					}
				} catch (Exception e) {
					System.out.printf("error to press '%s': %s\n", c, e.getMessage());
				}
			}
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isSpecialCharacter(char c) {
		return SPECIAL_CHARACTERS.containsKey(c) || !Character.isLetterOrDigit(c);
	}

	private static int getKeyCode(char c) {
		if (SPECIAL_CHARACTERS.containsKey(c)) {
			return SPECIAL_CHARACTERS.get(c);
		}
		return KeyEvent.getExtendedKeyCodeForChar(c);
	}

	private static void setEnglish() {
		try {
			String currentLanguage = InputContext.getInstance().getLocale().toString();
			if (!currentLanguage.equals("en_US")) {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}
}
