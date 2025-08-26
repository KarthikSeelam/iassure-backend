package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter@Setter
public class FieldSubmissionRequest {
    private List<Map<String, Object>> fields;
}
