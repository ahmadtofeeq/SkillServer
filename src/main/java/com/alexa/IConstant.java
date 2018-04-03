package com.alexa;

public interface IConstant {
	/* Constant for generating lwa access_token */
	String lwaurl = "https://api.amazon.com/auth/O2/token";
	String lwagrant_type = "grant_type", lwaclient_id = "client_id", lwaclient_secret = "client_secret",
			lwascope = "scope", lwaContentType = "Content-Type";
	String client_id = "amzn1.application-oa2-client.a03d7ace942a450fa4f9120ccd3c677f",
			client_secret = "c81a2f41fbc6ad23457c9b59cf0db81fa0c9ce73ba528b05847dcf45938e3269";
}
