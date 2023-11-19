package com.mindlease.fa.util;

public enum TabValues {
	TAB1("order"), TAB2("geometry"), TAB3("reason"), TAB4("analysis"), TAB5("position"), TAB6("location"),
	TAB7("processing"), TAB8("costs"), TAB9("result"), TAB10("statistics");

	private final String value;

	private TabValues(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public TabValues getPrevious() {
		return this.ordinal()!=0?  TabValues.values()[this.ordinal() - 1] : null;
	}

	public TabValues getNext() {
		return this.ordinal()<(TabValues.values().length-1)? TabValues.values()[this.ordinal() + 1] : null;
	}

	public static TabValues[] getCreateTabs() {
		return new TabValues[]{TAB1, TAB2, TAB3, TAB4, TAB5, TAB6};
	}

	public TabValues getCreatePagePreviousTab() {
		return this.ordinal()!=0?  getCreateTabs()[this.ordinal() - 1] : null;
	}

	public TabValues getCreatePageNextTab() {
		return this.ordinal()<(getCreateTabs().length-1)? getCreateTabs()[this.ordinal() + 1] : null;
	}
}
