package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RequestChatDto {
    private int incidentId;
    private String comments;
    private int userId;
}
