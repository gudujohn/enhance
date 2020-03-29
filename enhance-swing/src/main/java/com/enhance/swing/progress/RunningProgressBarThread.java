package com.enhance.swing.progress;

import javax.swing.*;

import com.enhance.swing.message.MessageUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunningProgressBarThread extends Thread {

	private JProgressBar progressBar;

	public static boolean stopFlag = true;

	public RunningProgressBarThread(JProgressBar progressBar) {
		this.progressBar = progressBar;
		this.start();
	}

	@Override
	public void run() {
		stopFlag = false;
		int value = 0;
		boolean pp = true;
		while (!stopFlag) {
			progressBar.setValue(value);
			if (value == 100) {
				pp = false;
			} else if (value == 0 && !pp) {
				pp = true;
			}
			if (pp) {
				value++;
			} else {
				value--;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("线程出错", e);
				MessageUtil.showMessageDialog_Error_Safe("线程出错" + e.getMessage());
			}
		}
	}

}
