package com.troila.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="policy.crawler")
public class PolicySettings {
	
	private String serverUrl;

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	@Override
	public String toString() {
		return "PolicySettings [serverUrl=" + serverUrl + "]";
	}
	
}
