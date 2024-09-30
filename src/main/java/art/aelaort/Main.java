package art.aelaort;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		sleep(3);
		press(getTextFromClipboard());
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

	private static void press(String text) {
		try {
			Robot robot = new Robot();
			char[] charArray = text.toCharArray();
			for (char c : charArray) {
				try {
					if (Character.isUpperCase(c)) {
						robot.keyPress(KeyEvent.VK_SHIFT);

						robot.keyPress(Character.toUpperCase(c));
						robot.keyRelease(Character.toUpperCase(c));

						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else {
						robot.keyPress(Character.toUpperCase(c));
						robot.keyRelease(Character.toUpperCase(c));
					}
				} catch (Exception e) {
					System.out.printf("error to press '%s' character: %d, %d\n", c,
							((int) Character.toUpperCase(c)), ((int) c));
				}
			}
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}
}
