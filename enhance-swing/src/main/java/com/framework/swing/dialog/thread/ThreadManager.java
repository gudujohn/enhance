package com.framework.swing.dialog.thread;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

public class ThreadManager {
	public static void runWithProgress(IJob job) {
		new JobThread(job, getBlockProgress(job.getComponent(), job.getAutoIncrease(), job.isPercentPrompt())).start();
	}

	public static IJobMonitor getBlockProgress(Component owner, int autoIncrease, boolean isPercentPrompt) {
		IJobMonitor monitor = null;
		if (owner instanceof Frame) {
			monitor = new BlockProgressMonitor((Frame) owner, autoIncrease, isPercentPrompt);
		} else if (owner instanceof Dialog) {
			monitor = new BlockProgressMonitor((Dialog) owner, autoIncrease, isPercentPrompt);
		}
		return monitor;
	}
}
