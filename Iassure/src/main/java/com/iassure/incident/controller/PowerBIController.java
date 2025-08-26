package com.iassure.incident.controller;

import com.iassure.incident.service.PowerBIService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/powerbi")
public class PowerBIController {
    @Autowired
    private PowerBIService powerBiService;

    @GetMapping("/getEmbedInfo")
    public Map<String, Object> getEmbedInfo() {
        try {
            String accessToken = powerBiService.getAccessToken();
            JSONObject embedTokenResponse = powerBiService.getEmbedToken(accessToken);

            Map<String, Object> embedInfo = new HashMap<>();
            embedInfo.put("embedToken", embedTokenResponse.getString("token"));
            embedInfo.put("embedUrl", embedTokenResponse.getString("embedUrl"));
            embedInfo.put("reportId", embedTokenResponse.getString("reportId"));

            return embedInfo;
        } catch (Exception e) {
            throw new RuntimeException("Error generating embed info", e);
        }
    }
}
