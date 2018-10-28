package org.usfirst.frc.team1257.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EnhancedDashboard extends SmartDashboard {
	private static Map<String, Runnable> buttons = new HashMap<>();

	public static void putNumber(String keyName) {
		putNumber(keyName, 0);
	}

	public static void putString(String keyName) {
		putString(keyName, "");
	}

	public static void putBoolean(String keyName) {
		putBoolean(keyName, false);
	}

	public static double getNumber(String keyName) {
		return getNumber(keyName, 0);
	}

	public static String getString(String keyName) {
		return getString(keyName, "");
	}

	public static boolean getBoolean(String keyName) {
		return getBoolean(keyName, false);
	}

	public static void addButton(String buttonName, Runnable callback) {
		if (!buttons.containsKey(buttonName)) {
			buttons.put(buttonName, callback);
			putBoolean(buttonName);
		}
	}

	public static void removeButton(String buttonName) {
		buttons.remove(buttonName);
	}

	public static void updateButtons() {
		Iterator<Entry<String, Runnable>> iterator = buttons.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Runnable> pair = iterator.next();
			String buttonName = pair.getKey();
			if (getBoolean(buttonName)) {
				putBoolean(buttonName, false);
				pair.getValue().run();
			}
		}
	}
}
