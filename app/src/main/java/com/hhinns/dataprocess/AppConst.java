package com.hhinns.dataprocess;

public class AppConst {

	public static final String TAG = "insthub";
	public static final int BALL_COLOR_RED = 0;
	public static final int BALL_COLOR_BLUE = 0;
	public static final Boolean DEBUG = true;
	public static final String PIC_DIR_PATH = "/insthub/pic";
	public static final int MAX_CONTENT_LEN = 140;

	public static final int RESULT_PHOTO_PREVIEW = 2;

	public static final String ROOT_DIR_PATH = "/insthub/cache";
	public static final String CACHE_DIR_PATH = ROOT_DIR_PATH + "/file";
	public static final String LOG_DIR_PATH = ROOT_DIR_PATH + "log";

	public static final int ENVIRONMENT_PRODUCTION = 1;
	public static final int ENVIROMENT_DEVELOPMENT = 2;
	public static final int ENVIROMENT_MOCKSERVER = 3;

	public static int environment() {
		return ENVIRONMENT_PRODUCTION;
	}

}
