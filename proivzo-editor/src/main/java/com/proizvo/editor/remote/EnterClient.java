package com.proizvo.editor.remote;

import static com.proizvo.editor.remote.EnterHelper.*;
import com.google.gson.JsonObject;
import javax.ws.rs.client.*;
import javax.ws.rs.*;

public class EnterClient {

    private static final String BASE_URI = "http://localhost:8084/RestServer/api";

    private final Client client;
    private final WebTarget target;

    public EnterClient() {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URI).path("enter/v1");
    }

    public <T> T getVersion(Class<T> responseType) throws ClientErrorException {
        return target.request().get(responseType);
    }

    public void login() throws ClientErrorException {
        JsonObject json = toJsonObject(getSystemInfo());
        target.request().post(Entity.json(json));
    }

    public void close() {
        client.close();
    }
}
