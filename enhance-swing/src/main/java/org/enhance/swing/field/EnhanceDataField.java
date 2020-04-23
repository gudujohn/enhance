package org.enhance.swing.field;

import org.enhance.swing.combobox.DateCombobox;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnhanceDataField extends AbstractFieldPanel {

	private static final long serialVersionUID = 636165428871544289L;

	private DateCombobox dateField = new DateCombobox();

	public EnhanceDataField(String fieldName) {
		super(fieldName);
	}

	@Override
	protected void initView() {
		dateField = new DateCombobox();
		this.add(dateField, "1w");
	}

}