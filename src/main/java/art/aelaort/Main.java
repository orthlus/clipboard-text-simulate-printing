package art.aelaort;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
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
