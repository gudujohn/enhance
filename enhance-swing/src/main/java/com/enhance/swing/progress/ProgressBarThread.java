package com.enhance.swing.progress;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;

import com.enhance.swing.message.MessageUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProgressBarThread extends Thread {

	private JProgressBar progressBar;
	private List<ProgressBarCallback> progressBarCallbacks;

	private boolean mistake;
	private boolean result;

	public ProgressBarThread(JProgressBar bar, List<ProgressBarCallback> progressBarCallbacks) {
		progressBar = bar;
		this.progressBarCallbacks = progressBarCallbacks;
	}

	@Override
	public void run() {
		for (final ProgressBarCallback progressBarCallback : progressBarCallbacks) {
			final CountDownLatch end = new CountDownLatch(1);
			// 启动一个独立的线程执行进度条中的加载逻辑
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						result = progressBarCallback.execute();
					} catch (Exception e) {
						log.info(progressBarCallback.progressName + "错误", e);
						MessageUtil.showMessageDialog_Information_Safe(progressBarCallback.progressName + "错误，错误原因：" + e.getMessage());
						mistake = true;
					} finally {
						if (result) {
							end.countDown();
						}
					}
				}
			}).start();
			// 这儿是进度条的显示逻辑
			try {
				for (int i = progressBarCallback.start; i < progressBarCallback.end; i = i + progressBarCallback.increment) {
					int value = progressBar.getValue();
					if (result == true) { // 如果加载逻辑结束后直接进度条置到end结束进度条前进
						progressBar.setValue(progressBarCallback.end);
						break;
					}
					if (mistake == true) { // 如果加载逻辑出错直接停止进度条
						break;
					}
					progressBar.setValue(value + progressBarCallback.increment);
					Thread.sleep(progressBarCallback.delay);
				}
				// 进度条加载结束但加载逻辑未结束那就在这儿等着
				end.await();
			} catch (InterruptedException e) {
				log.error("线程出错", e);
				MessageUtil.showMessageDialog_Error_Safe("线程出错" + e.getMessage());
			}
		}

	}

}
