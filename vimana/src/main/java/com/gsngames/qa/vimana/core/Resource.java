package com.gsngames.qa.vimana.core;

import java.io.File;

/**
 * This Class defines all the file and directory paths
 * 
 * Note:
 * logDir - will be appended with the date time  during the boostrap 
 * @author lnr
 *
 */
public class Resource {

    public final static String viewDtdPath = "src/test/resources/data/view.dtd";
    public final static String viewXmlPath = "src/test/resources/data/view.xml";
    public final static String testDataXmlPath = "src/test/resources/data/testdata.xml";

    public final static String testResourceDir = "src/test/resources";
    public final static String dataPath = "src/test/resources/data";
    public final static String migrationDataPath = "src/test/resources/data/migration";
    public final static String vmGridPath = "src/test/resources/data";

    public final static String yalaXmlPath = "src/test/resources/yala.xml";
    public final static String dynamicConfigPath = "src/test/resources/dynamic.config.properties";
    public final static String dynamicTestNG = "src/test/resources/dynamic.testng.xml";
    public final static String junitXml      = "JUNIT-FullReport.xml";
    public final static String testSuiteXml      = "TEST-TestSuite.xml";
    public final static String combinedReportXml = "COMBINED-TestSuite.xml";

    public final static String targetDir = "target";
    public static String logDir = targetDir + File.separator + "logs";
    public final static String reportDir = targetDir + File.separator + "reports";
    public final static String screenShotDir = targetDir + File.separator + "logs";
    public final static String logPropertyFile = testResourceDir + File.separator + "logging.properties" ;
    
  //Addred related to CC
    public final static String codeCoverageDir = targetDir + File.separator + "code_coverage"; 
    public static String CodeCoverageJSFile = codeCoverageDir + File.separator + "cover.js"; 
    public static String CodeCoverageReport = codeCoverageDir + File.separator + "codecoverage.html";
    
    
    //base tesng and config template files
    public final static String baseTestNGXml = "base.testng.xml";
    public final static String baseConfigXml = "src/test/resources/config.properties";
    
    
    //Report resources
    public final static String surefireReports  = "surefire-reports";
    public final static String failedRerun      = "failed-rerun";
    //private static final String[] suffix = {"initial", "rerun", "third_run", "4th"};
    
    public final static String JUNITReportDir = targetDir + File.separator + surefireReports + File.separator + junitXml;
    public final static String TestNGReportDir = targetDir + File.separator + surefireReports + File.separator + testSuiteXml;
    public final static String TestNGRerunReportDir = targetDir + File.separator + surefireReports + File.separator + failedRerun + File.separator + testSuiteXml;
    public final static String TestNGCombinedReportXmlDir = targetDir + File.separator + surefireReports + File.separator + combinedReportXml;

}
