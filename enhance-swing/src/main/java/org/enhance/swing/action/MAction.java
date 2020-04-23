package org.enhance.swing.action;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * 按钮事件
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public abstract class MAction extends AbstractAction {

	private boolean isAllowed = true;

	public MAction() {
		super();
	}

	public MAction(String name) {
		super(name);
	}

	public MAction(Icon icon) {
		super();
		this.putValue(Action.SMALL_ICON, icon);
	}

	public MAction(Icon icon, String toolTipText) {
		super();
		this.putValue(Action.SMALL_ICON, icon);
		this.setToolTipText(toolTipText);
	}

	public MAction(String name, Icon icon) {
		super(name, icon);
	}

	public MAction(String name, String toolTipText, Icon icon) {
		this(name, icon);
		this.setToolTipText(toolTipText);
	}

	public MAction(String name, String toolTipText) {
		this(name);
		this.setToolTipText(toolTipText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isAllowed()) {
			execute(e);
		} else {
			Object resource = e.getSource();
			if (resource instanceof Component) {
				Component component = (Component) resource;
				JOptionPane.showMessageDialog(HandleFactory.getFrame(component), "该功能无权限访问或者已被禁用", "警告", JOptionPane.WARNING_MESSAGE);
				if (resource instanceof JToggleButton) {
					JToggleButton button = (JToggleButton) component;
					button.getModel().setSelected(!button.getModel().isSelected());
				}
			}
		}
	}

	public abstract void execute(ActionEvent e);

	public String getName() {
		return (String) getValue(Action.NAME);
	}

	public void setName(String name) {
		putValue(Action.NAME, name);
	}

	public String getToolTipText() {
		return (String) getValue(Action.SHORT_DESCRIPTION);
	}

	public void setToolTipText(String toolTipText) {
		putValue(Action.SHORT_DESCRIPTION, toolTipText);
	}

	public Icon getIcon() {
		return (Icon) getValue(Action.SMALL_ICON);
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

}
