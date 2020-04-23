package org.enhance.swing.dialog.thread;

import java.awt.*;

public abstract class AbstractJob implements IJob, Comparable<Object> {
	private String name;
	private Component component;
	private int autoIncrease = 5;
	private boolean isPercentPrompt = false;

	public AbstractJob(Component component) {
		this(component, false);
	}

	public AbstractJob(Component component, boolean isPercentPrompt) {
		this(component, 5, isPercentPrompt);
	}

	public AbstractJob(Component component, int autoIncrease) {
		this(component, autoIncrease, false);
	}

	public AbstractJob(Component component, int autoIncrease, boolean isPercentPrompt) {
		this((String) null, component, autoIncrease, isPercentPrompt);
	}

	public AbstractJob(String name, Component component) {
		this(name, component, false);
	}

	public AbstractJob(String name, Component component, boolean isPercentPrompt) {
		this(name, component, 5, isPercentPrompt);
	}

	public AbstractJob(String name, Component component, int autoIncrease) {
		this(name, component, autoIncrease, false);
	}

	public AbstractJob(String name, Component component, int autoIncrease, boolean isPercentPrompt) {
		this.name = name;
		this.component = component;
		this.autoIncrease = autoIncrease;
		this.isPercentPrompt = isPercentPrompt;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public int getAutoIncrease() {
		return autoIncrease;
	}

	@Override
	public boolean isPercentPrompt() {
		return isPercentPrompt;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof AbstractJob) {
			return getName().compareTo(((AbstractJob) o).getName());
		}
		return 0;
	}
}
