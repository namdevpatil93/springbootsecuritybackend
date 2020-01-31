package com.sts.springbootsecuritybackend.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to the Applicant.
 *
 * <p>
 *     Properties are configured in the applicant.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private final Resources resources = new Resources();
	private final Amazonhistoricaldb amazonhistoricaldb = new Amazonhistoricaldb();

	public Amazonhistoricaldb getAmazonhistoricaldb() {
		return amazonhistoricaldb;
	}
	public Resources getResources() {
		return this.resources;
	}
	public static class Resources {

		private String profile = "";

		public String getProfile() {
			return profile;
		}

		public void setProfile(String profile) {
			this.profile = profile;
		}
	
	}
	public static class Amazonhistoricaldb {
		private String schema="";
		private String host="";
		private String driverName = "";
		private String wsurl = "";
		private String wsurlUsername = "";
		private String wsurlPassword = "";

		public String getSchema() {
			return schema;
		}

		public void setSchema(String schema) {
			this.schema = schema;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
		
		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}

		public String getWsurl() {
			return wsurl;
		}

		public void setWsurl(String wsurl) {
			this.wsurl = wsurl;
		}

		public String getWsurlUsername() {
			return wsurlUsername.trim();
		}

		public void setWsurlUsername(String wsurlUsername) {
			this.wsurlUsername = wsurlUsername;
		}

		public String getWsurlPassword() {
			return wsurlPassword.trim();
		}

		public void setWsurlPassword(String wsurlPassword) {
			this.wsurlPassword = wsurlPassword;
		}
	}

	
}