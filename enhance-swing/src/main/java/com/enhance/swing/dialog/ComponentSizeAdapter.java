package com.enhance.swing.dialog;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ComponentSizeAdapter extends ComponentAdapter {
	private Dimension preferredSize;
	private Dimension minimumSize;
	private Dimension maximumSize;

	public ComponentSizeAdapter(Dimension preferredSize) {
		this(preferredSize, null, null);
	}

	public ComponentSizeAdapter(Dimension preferredSize, Dimension minimumSize, Dimension maximumSize) {
		this.preferredSize = preferredSize;
		this.minimumSize = minimumSize;
		this.maximumSize = maximumSize;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Component component = (Component) e.getSource();
		Dimension dimension = component.getSize();

		if (preferredSize == null) {
			return;
		}

		if (minimumSize != null) {
			if (dimension.width < minimumSize.width || dimension.height < minimumSize.height) {
				preferredSize = minimumSize;
				component.setSize(preferredSize);
			}
		}

		if (maximumSize != null) {
			if (dimension.width > maximumSize.width || dimension.height > maximumSize.height) {
				preferredSize = maximumSize;
				component.setSize(preferredSize);
			}
		}

		if (minimumSize == null && maximumSize == null) {
			component.setSize(preferredSize);
		}
	}
}
