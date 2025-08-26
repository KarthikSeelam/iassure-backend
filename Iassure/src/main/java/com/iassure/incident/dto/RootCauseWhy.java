package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootCauseWhy {
    private Integer problemStatusId;
    private String flag;
    private String stage;
    private String stageNumber;
    private String occur;
    private String undetected;
    private String prevented;
}
