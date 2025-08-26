package com.iassure.incident.response;

import com.iassure.incident.entity.CAPTaskMaster;
import com.iassure.incident.entity.DocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class GetTasksResponse {
    private StatusResponse statusResponse;
    private CAPTaskMaster capTaskMaster;
    private List<DocumentEntity> taskFiles;
}
