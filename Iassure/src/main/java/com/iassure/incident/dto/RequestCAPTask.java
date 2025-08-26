package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCAPTask {
    private String flag;
    private int capTaskId;
    private int incidentId;
    private int departmentId;
    private int taskId;
    private String taskName;
    private String comments;
    private String dueDate;
    private int resolvedFlag;
    private int createdBy;
}
