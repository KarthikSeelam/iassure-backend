package com.iassure.incident.service;

import com.iassure.config.PowerBIConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Log4j2
public class PowerBIService {
    @Autowired
    private PowerBIConfig config;

    public String getAccessToken() throws Exception {
        HttpPost post = getHttpPost();

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(responseBody);

            return json.getString("access_token");
        }
    }

    private @NotNull HttpPost getHttpPost() throws UnsupportedEncodingException {
        String url = "https://login.microsoftonline.com/" + config.getTenantId() + "/oauth2/v2.0/token";

        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        String body = String.format(
                "grant_type=client_credentials&client_id=%s&client_secret=%s&scope=https://analysis.windows.net/powerbi/api/.default",
                config.getClientId(),
                config.getClientSecret()
        );


        post.setEntity(new StringEntity(body));
        log.info("URL BODY Is {}",post);
        return post;
    }

    public JSONObject getEmbedToken(String accessToken) throws Exception {
        String url = String.format(
                "https://api.powerbi.com/v1.0/myorg/groups/%s/reports/%s/GenerateToken",
                config.getWorkspaceId(),
                config.getReportId()
        );

        HttpPost post = new HttpPost(url);
        post.addHeader("Authorization", "Bearer " + accessToken);
        post.addHeader("Content-Type", "application/json");

        String body = "{\"accessLevel\": \"View\"}";
        post.setEntity(new StringEntity(body));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            String responseBody = EntityUtils.toString(response.getEntity());
            return new JSONObject(responseBody);
        }
    }
}
