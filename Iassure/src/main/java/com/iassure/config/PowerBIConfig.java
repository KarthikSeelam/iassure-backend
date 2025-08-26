package com.iassure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter@Setter
public class PowerBIConfig {
    @Value("${azure.tenant.id}")
    private String tenantId;

    @Value("${azure.client.id}")
    private String clientId;

    @Value("${azure.client.secret}")
    private String clientSecret;

    @Value("${powerbi.workspace.id}")
    private String workspaceId;

    @Value("${powerbi.report.id}")
    private String reportId;
}
