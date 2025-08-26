package com.iassure.incident.response;

import com.iassure.incident.dto.MastersDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MasterSourceResponse {
    private StatusResponse statusResponse;
    private List<MastersDTO> masterList;
}
