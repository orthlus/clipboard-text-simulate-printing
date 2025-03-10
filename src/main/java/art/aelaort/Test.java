package art.aelaort;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Test {
	private static final long KEYPRESS_INTERVAL = 150;

	public static void simulateTyping(String text, long interval) throws AWTException {
		Robot robot = new Robot();
		for (char c : text.toCharArray()) {
			// Handle special characters (e.g., uppercase letters, symbols)
			if (Character.isUpperCase(c)) {
				robot.keyPress(KeyEvent.VK_SHIFT); // Press Shift for uppercase
			}

			// Convert character to key code
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (keyCode != KeyEvent.VK_UNDEFINED) {
				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);
			}

			if (Character.isUpperCase(c)) {
				robot.keyRelease(KeyEvent.VK_SHIFT); // Release Shift
			}

			// Wait between keypresses
			robot.delay((int) interval);
		}
	}

	public static void main(String[] args) throws Exception {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String text = (String) clipboard.getData(DataFlavor.stringFlavor);

		System.out.println(text);
		System.out.println();

		System.out.println("Waiting for 5 seconds...");
		Thread.sleep(5000);

		System.out.println("Simulating typing...");
		simulateTyping(text, KEYPRESS_INTERVAL);
		System.out.println("Done!");
	}
}
