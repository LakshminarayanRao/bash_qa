package com.gsngames.qa.sikuli.client;

import java.awt.Rectangle;

import org.sikuli.script.Region;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.gsngames.bashwebframe.data.NameValueMap;

public class SikuliClient {
	String url;
	RestTemplate restTemplate;

	public SikuliClient(String url) {
		this.url = url;
		this.restTemplate = new RestTemplate();
	}

	public NameValueMap getScreen() {
		String getScreenUrl = this.url + "getScreen";
		NameValueMap nameValueMap = this.restTemplate.getForObject(getScreenUrl, NameValueMap.class);
		return nameValueMap;
	}

	public NameValueMap moveTo(NameValueMap nameValueMap) {

		String moveToUrl = this.url + "moveTo";
		nameValueMap = this.restTemplate.postForEntity(moveToUrl, nameValueMap, NameValueMap.class).getBody();
		return nameValueMap;
	}

	public NameValueMap clickAt(NameValueMap nameValueMap) {

		String clickAtUrl = this.url + "clickAt";
		nameValueMap = this.restTemplate.postForEntity(clickAtUrl, nameValueMap, NameValueMap.class).getBody();
		return nameValueMap;
	}

	public static void main(String[] args) {
		String url = "http://localhost:8080/sikuli/";
		SikuliClient client = new SikuliClient(url);
		NameValueMap nameValueMap = new NameValueMap();
		nameValueMap.getMap().put("region.x", 600);
		nameValueMap.getMap().put("region.y", 600);
		nameValueMap.getMap().put("region.w", 0);
		nameValueMap.getMap().put("region.h", 0);
		
		nameValueMap = client.clickAt(nameValueMap);
		System.out.println(new Gson().toJson(nameValueMap));

	}

	/*
	 * private WebResource webResource; private String device;
	 * 
	 * public SikuliClient(WebResource webResource,String device) {
	 * this.webResource = webResource; this.device = device; }
	 * 
	 * public ClientResponse clearScreen() { NameValueMap nameValueMap = new
	 * NameValueMap(); nameValueMap.getMap().put("device", device ); return
	 * webResource.path("/sikuli/clearScreen").accept(MediaType.TEXT_PLAIN).post
	 * (ClientResponse.class,nameValueMap); }
	 * 
	 * 
	 * public ClientResponse scrollUpFull() throws Exception{ NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("device",
	 * device ); return
	 * webResource.path("/sikuli/scrollUpFull").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse shortScrollUp() throws Exception{ NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("device",
	 * device ); return
	 * webResource.path("/sikuli/shortScrollUp").type(MediaType.APPLICATION_XML)
	 * .accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse mediumScrollUp() throws Exception{ NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("device",
	 * device ); return
	 * webResource.path("/sikuli/mediumScrollUp").type(MediaType.APPLICATION_XML
	 * ).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * }
	 * 
	 * public ClientResponse mediumScrollDown() throws Exception{ NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("device",
	 * device ); return
	 * webResource.path("/sikuli/mediumScrollDown").type(MediaType.
	 * APPLICATION_XML).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class,
	 * nameValueMap); }
	 * 
	 * 
	 * public ClientResponse scrollDownFull() throws Exception{ NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("device",
	 * device ); return
	 * webResource.path("/sikuli/scrollDownFull").type(MediaType.APPLICATION_XML
	 * ).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * }
	 * 
	 * public ClientResponse webdriverClick( Point point,Dimension dimension)
	 * throws Exception{ NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("x", point.getX());
	 * nameValueMap.getMap().put("y", point.getY());
	 * nameValueMap.getMap().put("w", dimension.getWidth());
	 * nameValueMap.getMap().put("h", dimension.getHeight());
	 * 
	 * return
	 * webResource.path("/sikuli/webdriverClick").type(MediaType.APPLICATION_XML
	 * ).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * }
	 * 
	 * public ClientResponse click(String imgPath) throws Exception{
	 * NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("image", FileUtils.readFileToByteArray(new
	 * File(imgPath))); return
	 * webResource.path("/sikuli/click").type(MediaType.APPLICATION_XML).accept(
	 * MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse longClick(String imgPath, int time) throws
	 * Exception{
	 * 
	 * NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("image", FileUtils.readFileToByteArray(new
	 * File(imgPath))); ClientResponse response =
	 * webResource.path("/sikuli/hover").type(MediaType.APPLICATION_XML).accept(
	 * MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); response
	 * =
	 * webResource.path("/sikuli/mouseDownLeft").type(MediaType.APPLICATION_XML)
	 * .accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * Thread.sleep(time*1000); response =
	 * webResource.path("/sikuli/mouseUpLeft").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * return response; }
	 * 
	 * public ClientResponse typeSikuli(String imgPath, String textToType)
	 * throws Exception{ NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("image", FileUtils.readFileToByteArray(new
	 * File(imgPath))); nameValueMap.getMap().put("textToType", textToType);
	 * return
	 * webResource.path("/sikuli/typeSikuli").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse doubleClick(String imgPath) throws Exception{
	 * NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("image", FileUtils.readFileToByteArray(new
	 * File(imgPath))); return
	 * webResource.path("/sikuli/doubleClick").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse switchApp(String appName) { NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("appName",
	 * appName); return
	 * webResource.path("/sikuli/switchApp").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse closeApp(String appName) { NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("appName",
	 * appName); return
	 * webResource.path("/sikuli/closeApp").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse hover(String imgPath) throws Exception {
	 * NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("device", device );
	 * nameValueMap.getMap().put("image", FileUtils.readFileToByteArray(new
	 * File(imgPath))); return
	 * webResource.path("/sikuli/hover").type(MediaType.APPLICATION_XML).accept(
	 * MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse mouseDownLeft() { NameValueMap nameValueMap = new
	 * NameValueMap(); //nameValueMap.getMap().put("imgPath", imgPath); return
	 * webResource.path("/sikuli/mouseDownLeft").type(MediaType.APPLICATION_XML)
	 * .accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse mouseDownRight() { NameValueMap nameValueMap = new
	 * NameValueMap(); //nameValueMap.getMap().put("imgPath", imgPath); return
	 * webResource.path("/sikuli/mouseDownRight").type(MediaType.APPLICATION_XML
	 * ).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * }
	 * 
	 * public ClientResponse mouseUpLeft() { NameValueMap nameValueMap = new
	 * NameValueMap(); //nameValueMap.getMap().put("imgPath", imgPath); return
	 * webResource.path("/sikuli/mouseUpLeft").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public ClientResponse mouseUpRight() { NameValueMap nameValueMap = new
	 * NameValueMap(); //nameValueMap.getMap().put("imgPath", imgPath); return
	 * webResource.path("/sikuli/mouseUpLeft").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap); }
	 * 
	 * public boolean exists(String imgPath) throws Exception { NameValueMap
	 * nameValueMap = new NameValueMap(); nameValueMap.getMap().put("image",
	 * FileUtils.readFileToByteArray(new File(imgPath))); ClientResponse
	 * response =
	 * webResource.path("/sikuli/exists").type(MediaType.APPLICATION_XML).accept
	 * (MediaType.TEXT_PLAIN).post(ClientResponse.class, nameValueMap);
	 * if(response.getEntity(String.class).contains("Success")) return true;
	 * else return false; }
	 * 
	 * public NameValueMap getScreen(String screenStart,String screenEnd) throws
	 * Exception { NameValueMap nameValueMap = new NameValueMap();
	 * nameValueMap.getMap().put("screenStart",
	 * FileUtils.readFileToByteArray(new File(screenStart)));
	 * nameValueMap.getMap().put("screenEnd", FileUtils.readFileToByteArray(new
	 * File(screenEnd))); nameValueMap.getMap().put("device", device ); return
	 * webResource.path("/sikuli/getScreen").type(MediaType.APPLICATION_XML).
	 * accept(MediaType.APPLICATION_XML).post(NameValueMap.class, nameValueMap);
	 * }
	 */
}
