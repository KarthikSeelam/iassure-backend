package com.iassure.incident.response;

import com.iassure.incident.entity.MasterSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class AddMasterSourceResponse {
    private StatusResponse statusResponse;
    private MasterSource masterSource;
}
