package com.alexa;

public interface IConstant {
	/* Constant for generating lwa access_token */
	String lwaurl = "https://api.amazon.com/auth/O2/token";
	String lwagrant_type = "grant_type", lwaclient_id = "client_id", lwaclient_secret = "client_secret",
			lwascope = "scope", lwaContentType = "Content-Type";
	String client_id = "amzn1.application-oa2-client.b7b31e6ad3e644a5978a3a3b78a8a1b4",
			client_secret = "1aa2fc2800a8aea4e7fc20a71e7b918c9ef362145d0cf7e5812e2ad51d9806bf";
}
