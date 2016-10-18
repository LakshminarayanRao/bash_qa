package com.gsngames.qa.vimana.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Platform;
import org.testng.collections.Maps;
import org.testng.reporters.XMLConstants;

/**
 * Helper class
 * 
 * @author lnr
 * 
 */
public class VimanaUtil {

    private static final Pattern ENTITY = Pattern.compile("&[a-zA-Z]+;.*");
    private static DateFormat preferredFormat = new SimpleDateFormat("MMM d, HH:mm:ss");
    private static final Pattern LESS = Pattern.compile("<");
    private static final Pattern GREATER = Pattern.compile(">");
    private static final Pattern SINGLE_QUOTE = Pattern.compile("'");
    private static final Pattern QUOTE = Pattern.compile("\"");

    private static final Map<String, Pattern> ATTR_ESCAPES = Maps.newHashMap();

    static {
        ATTR_ESCAPES.put("&lt;", LESS);
        ATTR_ESCAPES.put("&gt;", GREATER);
        ATTR_ESCAPES.put("&apos;", SINGLE_QUOTE);
        ATTR_ESCAPES.put("&quot;", QUOTE);
    }

    public static String getCurrent() {
        return preferredFormat.format(new Date(System.currentTimeMillis()));
    }
    
    public static String getSimpleDate(Date date) {
        return preferredFormat.format(date);
    }

    /**
     * returns the intl string based on the account type and intl combination
     * 
     * @param intl
     * @param account
     * @return
     */
    public static String getIntlByAccountType(String intl, String account) {
        if (account != null && !account.trim().equals(""))
            return intl = intl + "-x-" + account;
        else
            return intl;

    }

    /**
     * Refer
     * http://www.theukwebdesigncompany.com/articles/entity-escape-characters
     * .php This site has complete details of the escape chars. This is for
     * future reference
     * 
     * @param aText
     * @return
     */
    public static String escapeForHTML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#39;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if (character == '%') {
                result.append("&#37;");
            } else {
                // the char is not a special one
                // add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    public static String getBaseTesNgXml() throws VimanaException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File baseTngFile = new File(classLoader.getResource(Resource.baseTestNGXml).getPath());
        try {
            return FileUtils.readFileToString(baseTngFile);
        } catch (IOException e) {
            throw new VimanaException("Error loading base testng xml from classpath", e.getCause());
        }
    }

    /**
     * The following three method is for emailing the report.
     * 
     * @param from
     * @param to
     * @param subject
     * @param fBody
     */
    public static void simpleEmailSender(String from, String to, String subject, File fBody) throws Exception {

        System.out.println("*****************************************************************");
        System.out.println("* From/FLANGE_TESTERSEMAIL: " + from);
        System.out.println("* TO/FLANGE_TOEMAIL: " + to);
        System.out.println("* Subject/FLANGE_EmailSubject: " + subject);
        System.out.println("*****************************************************************");
        if (to == null || to.length() == 0) {
            System.out.println(" No receiptent email address(es). Email is NOT sent.");
            return;
        }

        if (from == null || from.length() == 0) {
            from = "flange@gari.com";
        }
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.smtp.host", "smarthost.gari.com");
            Session session = Session.getDefaultInstance(props, null);
            // -- Create a new message --
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(getMimeMessageBody(fBody));
            msg.setContent(multipart);
            msg.setHeader("X-Mailer", "sendhtml");
            msg.setSentDate(new Date());

            // send the thing off
            Transport.send(msg);
            System.out.println("\nMail was sent successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private static BodyPart getMimeMessageBody(File body) throws MessagingException, IOException {
        BodyPart messageBodyPart = new MimeBodyPart();
        String message = getFileContents(body);
        messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(message, "text/html")));
        return messageBodyPart;
    }

    private static String getFileContents(File body) throws MessagingException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(body));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void setDateTimeProperties(Properties p, Date start, Date end) {
        double totalSec = (end.getTime() - start.getTime()) / 1000.0;
        p.setProperty("seconds", "" + totalSec);
        p.setProperty(XMLConstants.ATTR_TIME, getFormatedElapsedTime(totalSec));
        p.setProperty("start", preferredFormat.format(start));
        p.setProperty("end", preferredFormat.format(end));
    }

    public static String getFormatedElapsedTime(double elapsedTime) {
        String format = String.format("%%0%dd", 2);

        String seconds = String.format(format, (int) (elapsedTime % 60));
        String minutes = String.format(format, (int) ((elapsedTime % 3600) / 60));
        if (elapsedTime > 3600) {
            String hours = String.format(format, (int) (elapsedTime / 3600));
            return hours + ":" + minutes + ":" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }

    public static String encodeAttr(String attr) {
        String result = replaceAmpersand(attr, ENTITY);
        for (Map.Entry<String, Pattern> e : ATTR_ESCAPES.entrySet()) {
            result = e.getValue().matcher(result).replaceAll(e.getKey());
        }
        return result;
    }

    public static void setDateTimeProperties(Properties p, long start, long end) {
        setDateTimeProperties(p, new Date(start), new Date(end));
    }

    private static String replaceAmpersand(String str, Pattern pattern) {
        int start = 0;
        int idx = str.indexOf('&', start);
        if (idx == -1)
            return str;
        StringBuffer result = new StringBuffer();
        while (idx != -1) {
            result.append(str.substring(start, idx));
            if (pattern.matcher(str.substring(idx)).matches()) {
                // do nothing it is an entity;
                result.append("&");
            } else {
                result.append("&amp;");
            }
            start = idx + 1;
            idx = str.indexOf('&', start);
        }
        result.append(str.substring(start));

        return result.toString();
    }

    public static Platform getPlatform(String platform) {
        if (platform.equalsIgnoreCase("windows")) {
            return Platform.WINDOWS;
        } else if (platform.equalsIgnoreCase("unix")) {
            return Platform.UNIX;
        } else if (platform.equalsIgnoreCase("linux")) {
            return Platform.LINUX;
        } else if (platform.equalsIgnoreCase("mac")) {
            return Platform.MAC;
        } else if (platform.equalsIgnoreCase("vista")) {
            return Platform.VISTA;
        } else if (platform.equalsIgnoreCase("android")) {
            return Platform.ANDROID;
        } else if (platform.equalsIgnoreCase("xp")) {
            return Platform.XP;
        } else {
            return Platform.ANY;
        }
    }

    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String join(String[] strings, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (i != 0)
                sb.append(separator);
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    public static JSONObject getJsonObject(String query, String methodName, String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", new String(query));
        JSONObject operationObj = new JSONObject();
        operationObj.put("method_name", new String(methodName));
        JSONArray jsonArray = new JSONArray(Arrays.asList(args));
        operationObj.put("arguments", jsonArray);
        jsonObject.put("operation", operationObj);
        jsonObject.put("selector_engine", "shelley_compat");

        return jsonObject;
    }

    public static String getJsonResponseAsString(String outcome, String[] results, String reason, String detail) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outcome", outcome);
        if (outcome.equalsIgnoreCase("success")) {
            jsonObject.put("results", new JSONArray(Arrays.asList(results)));
        } else {
            jsonObject.put("reason", reason);
            jsonObject.put("detail", detail);
        }
        return jsonObject.toString();
    }


    /**
     * Get the json string representation of request body
     * 
     * @param query
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public static JSONObject getJsonString(String query, String methodName, String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", new String(query));
        JSONObject operationObj = new JSONObject();
        operationObj.put("method_name", new String(methodName));
        JSONArray jsonArray = new JSONArray(Arrays.asList(args));
        operationObj.put("arguments", jsonArray);
        jsonObject.put("operation", operationObj);
        return jsonObject;
    }
}
