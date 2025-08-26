package com.iassure.incident.response;

import com.iassure.incident.entity.Fields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class FieldsResponse {
    private StatusResponse statusResponse;
    private List<Fields> dynamicFields;
}
