package com.iassure.incident.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter@Setter
public class IncidentFieldsDTO {

    private Integer fieldId;

    private Integer incidentFieldId;

    private String type;

    private String label;

    private String options;

    private String selectedOption;
}
