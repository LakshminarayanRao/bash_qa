package com.gsngames.qa.vimana.core;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Constants {

	public static final String CLOUD_USER = "dev";
	public static final String CLOUD_PASSWORD = "dev-pass";
	public static final String VM_GRID_MAP = "vmGrid";

	public static final String VM_GRID_KEY = "vmCloud";
	public static final String VM_TRACKR_MAP = "vmTracker";
	public static final String TEST_REPORT_MAP = "testReport";

	public static final String PRODUCT_MAP = "productMap";
	public static final String POOL_MAP = "poolMap";
	public static final String VM_MAP = "vmMap";
	public static final String CAPABILITY_MAP = "capabilityMap";
	public static final String PRODUCT_VERSION_MAP = "productVersionMap";
	public static final String TESTSUITE_MAP = "testSuiteMap";
	public static final String USERSTORY_MAP = "userStoryMap";
	public static final String SCENARIO_MAP = "scenarioMap";
	public static final String RUN_ID_MAP = "runIdMap";

	public static final String VM_TRACKR_STATUS_FREE = "FREE";
	public static final String VM_TRACKR_STATUS_INUSE = "INUSE";
	public static final String VM_TRACKR_STATUS_OFFLINE = "OFFLINE";

	public static final String CAPABILITY_KEY_ANDROID = "android";
	public static final String CAPABILITY_KEY_ANDROID_ND = "nd";
	public static final String CAPABILITY_KEY_ANDROID_CB = "cb";
	public static final String CAPABILITY_KEY_ANDROID_WD = "wd";

	public static final String CAPABILITY_KEY_IOS = "ios";
	public static final String CAPABILITY_KEY_IOS_CB = "cb";
	public static final String CAPABILITY_KEY_IOS_FRANK = "frank";
	public static final String CAPABILITY_KEY_IOS_WD = "wd";
	
	public static final String CAPABILITY_KEY_WD = "wd";
	public static final String DIRPATH= "\""+System.getProperty("user.home") + File.separator + "flange" + File.separator + "wd"+"\"" ;

	// Android skin and Memory Values
	public static final String ANDROIDSKIN = "HVGA";
	public static final String ANDROIDCAPACITY = "512";
	
	public static int FLANGEREMOTECLIENT_SERVER_PORT = 9998;
	
	public static int FLANGE_PROXY_PORT = 9777;
	public static int ANDROID_ND_PORT = 54129;
	public static int ANDROID_WD_PORT = 8080;
	public static int ANDROID_CB_PORT = 34777;
	public static int ANDROID_CB_INTERNALPORT= 7101;
	
	public static int IOS_WD_PORT = 3001;

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static final String getWDJarPath(String jarVersion) {
		return "\""+System.getProperty("user.home") + File.separator + "flange" + File.separator + "wd"+File.separator+ "selenium-server-standalone-" + jarVersion + ".jar"+"\"";
	}
	
	public static void main(String[] args) throws Exception {
		
		try {
		    Runtime r = Runtime.getRuntime();
		    String myScript = "java -jar  /Users/hbabu/flange/wd/selenium-server-standalone-2.25.0.jar";
		    String[] cmdArray = {"xterm", "-e", myScript + " ; le_exec"};
		    r.exec(cmdArray).waitFor();
		} catch (InterruptedException ex){
		    ex.printStackTrace();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	}
}
