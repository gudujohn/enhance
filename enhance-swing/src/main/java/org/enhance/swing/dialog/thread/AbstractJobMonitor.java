package org.enhance.swing.dialog.thread;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJobMonitor implements IJobMonitor {

	private List<IJobListener> listeners = new ArrayList<>();

	@Override
	public void addJobListener(IJobListener l) {
		listeners.add(l);
	}

	@Override
	public void fireJobStart() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).beforeJob();
		}
	}

	@Override
	public void fireJobErrorHappened(Exception ex) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).jobErrorHappened(ex);
		}
	}

	@Override
	public void fireJobCompeleted() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).afterJob();
		}
	}

	@Override
	public void fireJobCanceled() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).jobCanceled();
		}
	}
}
