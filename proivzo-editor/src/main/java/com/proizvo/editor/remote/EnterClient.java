package com.proizvo.editor.remote;

import static com.proizvo.editor.util.Networks.*;
import static com.proizvo.editor.remote.EnterHelper.*;
import com.google.gson.JsonObject;
import com.proizvo.editor.cloud.Authenticator;
import com.proizvo.editor.cloud.Credentials;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class EnterClient {

    private final String baseUri;
    private final Client client;
    private final WebTarget target;
    private final Authenticator auth;

    public EnterClient() {
        baseUri = getPartner(checkFlag(EnterClient.class));
        client = ClientBuilder.newClient().register(auth = new Authenticator());
        target = client.target(baseUri).path("enter/v1");
    }

    public <T> T getVersion(Class<T> responseType) throws ClientErrorException {
        return target.request().get(responseType);
    }

    public boolean login(Credentials creds) throws ClientErrorException {
        auth.setCredentials(creds);
        try {
            JsonObject json = toJsonObject(getSystemInfo());
            Response rsp = target.request().post(Entity.json(json + ""));
            return rsp.readEntity(String.class).equalsIgnoreCase("true");
        } catch (ProcessingException pe) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, pe.getMessage());
            return false;
        }
    }

    public void close() {
        client.close();
    }
}
