package com.mindlease.fa.util;

public class FailureAnalysisConstants {
	private FailureAnalysisConstants() {}
	public static final int BUTTONS_TO_SHOW = 3;
	public static final int INITIAL_PAGE = 0;
	//public static final int INITIAL_PAGE_SIZE = 500;
	//public static final int[] PAGE_SIZES = { 500, 1000, 1500 };
	
	public static final int INITIAL_PAGE_SIZE = 20;
	public static final int[] PAGE_SIZES = {20, 30, 40, -1 };


	public static final String ORDER = "order";
	public static final String GEOMETRY = "geometry";
	public static final String REASON = "reason";
	public static final String ANALYSIS = "analysis";
	public static final String LOCATION = "location";
	public static final String PROCESSING = "processing";
	public static final String COSTS = "costs";
	public static final String RESULT = "result";
	public static final String CURRENT_TAB = "currentTab";
	public static final String PREVIOUS_TAB = "previousTab";
	public static final String NEXT_TAB = "nextTab";

	public static final String ORDER_DETAILS = "orderDetails";
	public static final String PRIORITY_LIST = "priorityList";
	public static final String LOCATIONS_LIST = "locationsList";
	public static final String CUSTOMERS_LIST = "customersList";
	public static final String MATERIALS_LIST = "materialsList";
	public static final String PROCESS_STATUS_LIST = "processStatusList";
	public static final String SAMPLES_REMAIN_LIST = "samplesRemainList";

	public static final String FLASH = "flash";
	public static final String ORDER_UPDATE = "Order updated!";
	public static final String ORDER_SUCCESSFULLY_COMPLETED = "Order successfully completed!";
}
