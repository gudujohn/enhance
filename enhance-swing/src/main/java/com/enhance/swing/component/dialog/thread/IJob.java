package com.enhance.swing.component.dialog.thread;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public interface IJob {
	void run(IJobMonitor monitor) throws InvocationTargetException, InterruptedException;

	String getName();

	Component getComponent();

	int getAutoIncrease();

	boolean isPercentPrompt();
}
