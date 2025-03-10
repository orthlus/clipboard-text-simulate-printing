package art.aelaort;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Main2 {
	private static final Map<Character, KeyInfo2> SPECIAL_CHARACTERS = new HashMap<>();
	record KeyInfo2(int keyCode, boolean requiresShift) {}

	static {
		SPECIAL_CHARACTERS.put('!', new KeyInfo2(KeyEvent.VK_1, true));
		SPECIAL_CHARACTERS.put('@', new KeyInfo2(KeyEvent.VK_2, true));
		SPECIAL_CHARACTERS.put('#', new KeyInfo2(KeyEvent.VK_3, true));
		SPECIAL_CHARACTERS.put('$', new KeyInfo2(KeyEvent.VK_4, true));
		SPECIAL_CHARACTERS.put('%', new KeyInfo2(KeyEvent.VK_5, true));
		SPECIAL_CHARACTERS.put('^', new KeyInfo2(KeyEvent.VK_6, true));
		SPECIAL_CHARACTERS.put('&', new KeyInfo2(KeyEvent.VK_7, true));
		SPECIAL_CHARACTERS.put('*', new KeyInfo2(KeyEvent.VK_8, true));
		SPECIAL_CHARACTERS.put('(', new KeyInfo2(KeyEvent.VK_9, true));
		SPECIAL_CHARACTERS.put(')', new KeyInfo2(KeyEvent.VK_0, true));
		SPECIAL_CHARACTERS.put('-', new KeyInfo2(KeyEvent.VK_MINUS, false));
		SPECIAL_CHARACTERS.put('_', new KeyInfo2(KeyEvent.VK_MINUS, true));
		SPECIAL_CHARACTERS.put('=', new KeyInfo2(KeyEvent.VK_EQUALS, false));
		SPECIAL_CHARACTERS.put('+', new KeyInfo2(KeyEvent.VK_EQUALS, true));
		SPECIAL_CHARACTERS.put('[', new KeyInfo2(KeyEvent.VK_OPEN_BRACKET, false));
		SPECIAL_CHARACTERS.put(']', new KeyInfo2(KeyEvent.VK_CLOSE_BRACKET, false));
		SPECIAL_CHARACTERS.put('{', new KeyInfo2(KeyEvent.VK_OPEN_BRACKET, true));
		SPECIAL_CHARACTERS.put('}', new KeyInfo2(KeyEvent.VK_CLOSE_BRACKET, true));
		SPECIAL_CHARACTERS.put(';', new KeyInfo2(KeyEvent.VK_SEMICOLON, false));
		SPECIAL_CHARACTERS.put(':', new KeyInfo2(KeyEvent.VK_SEMICOLON, true));
		SPECIAL_CHARACTERS.put('\'', new KeyInfo2(KeyEvent.VK_QUOTE, false));
		SPECIAL_CHARACTERS.put('"', new KeyInfo2(KeyEvent.VK_QUOTE, true));
		SPECIAL_CHARACTERS.put(',', new KeyInfo2(KeyEvent.VK_COMMA, false));
		SPECIAL_CHARACTERS.put('.', new KeyInfo2(KeyEvent.VK_PERIOD, false));
		SPECIAL_CHARACTERS.put('<', new KeyInfo2(KeyEvent.VK_COMMA, true));
		SPECIAL_CHARACTERS.put('>', new KeyInfo2(KeyEvent.VK_PERIOD, true));
		SPECIAL_CHARACTERS.put('/', new KeyInfo2(KeyEvent.VK_SLASH, false));
		SPECIAL_CHARACTERS.put('?', new KeyInfo2(KeyEvent.VK_SLASH, true));
		SPECIAL_CHARACTERS.put('\\', new KeyInfo2(KeyEvent.VK_BACK_SLASH, false));
		SPECIAL_CHARACTERS.put('|', new KeyInfo2(KeyEvent.VK_BACK_SLASH, true));
		SPECIAL_CHARACTERS.put('`', new KeyInfo2(KeyEvent.VK_BACK_QUOTE, false));
		SPECIAL_CHARACTERS.put('~', new KeyInfo2(KeyEvent.VK_BACK_QUOTE, true));
	}

	public static void simulateTyping(String text) throws AWTException {
		Robot robot = new Robot();
		for (char c : text.toCharArray()) {
			// Handle uppercase letters and special characters
			boolean isUppercase = Character.isUpperCase(c);
			boolean isSpecialChar = SPECIAL_CHARACTERS.containsKey(c);

			// Press Shift if needed
			if (isUppercase || (isSpecialChar && SPECIAL_CHARACTERS.get(c).requiresShift)) {
				robot.keyPress(KeyEvent.VK_SHIFT);
			}

			// Handle special characters
			if (isSpecialChar) {
				int keyCode = SPECIAL_CHARACTERS.get(c).keyCode;
				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);
			} else {
				// Handle regular characters
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
				if (keyCode != KeyEvent.VK_UNDEFINED) {
					robot.keyPress(keyCode);
					robot.keyRelease(keyCode);
				}
			}

			if (isUppercase || (isSpecialChar && SPECIAL_CHARACTERS.get(c).requiresShift)) {
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}

//			robot.delay(20);
		}
	}

	public static void main(String[] args) throws Exception {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String text = (String) clipboard.getData(DataFlavor.stringFlavor);
		Thread.sleep(3000);
		simulateTyping(text);
	}
}
