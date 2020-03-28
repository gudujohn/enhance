package com.framework.swing.dialog.thread;

public interface IProgressMonitor {
	void begin(String taskname);

	void setLabel(String label);

	void setIndeterminate(boolean indeterminate);

	void setProgress(double value);

	void done();

	void dispose();

	void setCanceled(boolean cancel);

	boolean isCanceled();

}
