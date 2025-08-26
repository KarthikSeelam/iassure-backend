package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RootCauseAnalysis {
    private String flag;
    private int rootCauseId;
    private int incidentId;
    private int userId;
    private int orgId;
    private String problemDescription;
    private String problemWhy;
    private String rootCauseSummary;
    private List<RootCauseWhy> problems;
}
