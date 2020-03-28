package com.framework.swing.dialog.thread;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

public interface IJob {
	void run(IJobMonitor monitor) throws InvocationTargetException, InterruptedException;

	String getName();

	Component getComponent();

	int getAutoIncrease();

	boolean isPercentPrompt();
}
