package com.gsngames.qa.vimana.core.client;

public class VimanaRemoteClient {

	private String hostIp;
	int FLANGEREMOTECLIENT_SERVER_PORT = 9998;
	private SikuliClient sikuliClient;

	public VimanaRemoteClient(String hostIp, String device) {
		this.hostIp = hostIp;
		this.sikuliClient = new SikuliClient();
	}

	public String getBaseUri() {
		return "http://" + this.hostIp + ":" + FLANGEREMOTECLIENT_SERVER_PORT + "/flange/";
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public SikuliClient getSikuliClient() {
		return sikuliClient;
	}

	public void setSikuliClient(SikuliClient sikuliClient) {
		this.sikuliClient = sikuliClient;
	}

	public void shutDownclients() {
		if (this.sikuliClient != null) {
			this.sikuliClient = null;
		}
	}
}
