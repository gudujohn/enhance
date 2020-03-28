package com.framework.mswing.message;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.framework.mswing.utils.SwingUtil;

/**
 * 消息工具
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class MessageUtil {
	public static void showMessageDialog_Information(String content) {
		Component parentComponent = SwingUtil.getTopComponent();
		if (parentComponent == null) {
			parentComponent = Frame.getFrames()[Frame.getFrames().length - 1];
		}
		JOptionPane.showMessageDialog(parentComponent, content, MessageType.Information.getText(), MessageType.Information.getValue());
	}

	public static void showMessageDialog_Warnning(String content) {
		Component parentComponent = SwingUtil.getTopComponent();
		if (parentComponent == null) {
			parentComponent = Frame.getFrames()[Frame.getFrames().length - 1];
		}
		JOptionPane.showMessageDialog(parentComponent, content, MessageType.Warnning.getText(), MessageType.Warnning.getValue());
	}

	public static void showMessageDialog_Error(String content) {
		Component parentComponent = SwingUtil.getTopComponent();
		if (parentComponent == null) {
			parentComponent = Frame.getFrames()[Frame.getFrames().length - 1];
		}
		JOptionPane.showMessageDialog(parentComponent, content, MessageType.Error.getText(), MessageType.Error.getValue());
	}

	public static int showConfirmDialog_YesOrNo(String content) {
		Component parentComponent = SwingUtil.getTopComponent();
		if (parentComponent == null) {
			parentComponent = Frame.getFrames()[Frame.getFrames().length - 1];
		}
		return JOptionPane.showConfirmDialog(parentComponent, content, MessageType.YesOrNo.getText(), MessageType.YesOrNo.getValue());
	}

	public static void showExceptionDialog(String content) {
		Component parentComponent = SwingUtil.getTopComponent();
		if (parentComponent == null) {
			parentComponent = Frame.getFrames()[Frame.getFrames().length - 1];
		}
		JOptionPane.showMessageDialog(parentComponent, content + MessageType.Error.getText(), MessageType.Error.getText(), MessageType.Error.getValue());
	}

	public static void showMessageDialog_Information_Safe(final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MessageUtil.showMessageDialog_Information(content);
			}
		});
	}

	public static void showExceptionDialog_Safe(final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MessageUtil.showExceptionDialog(content);
			}
		});
	}

	public static void showMessageDialog_Warnning_Safe(final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MessageUtil.showMessageDialog_Warnning(content);
			}
		});
	}

	public static void showMessageDialog_Error_Safe(final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MessageUtil.showMessageDialog_Error(content);
			}
		});
	}

	public static int showConfirmDialog_YesOrNo_Safe(final String content) {
		Runnable r = new Runnable() {
			int confirm;

			@Override
			public void run() {
				confirm = JOptionPane.showConfirmDialog(Frame.getFrames()[0], content, MessageType.YesOrNo.getText(), MessageType.YesOrNo.getValue());
			}

			@Override
			public String toString() {
				return String.valueOf(confirm);
			}
		};
		SwingUtilities.invokeLater(r);
		return Integer.valueOf(r.toString());
	}
}
