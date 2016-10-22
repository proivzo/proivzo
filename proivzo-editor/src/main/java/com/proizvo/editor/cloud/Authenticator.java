package com.proizvo.editor.cloud;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

public class Authenticator implements ClientRequestFilter {

    private Credentials creds;

    @Override
    public void filter(ClientRequestContext crc) throws IOException {
        MultivaluedMap<String, Object> headers = crc.getHeaders();
        final String basicAuth = getBasicAuthentication();
        headers.add(HttpHeaders.AUTHORIZATION, basicAuth);
    }

    private String getBasicAuthentication() throws UnsupportedEncodingException {
        String token = creds.getUser() + ":" + creds.getPassword();
        return "Basic " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
    }

    public void setCredentials(Credentials creds) {
        this.creds = creds;
    }
}
