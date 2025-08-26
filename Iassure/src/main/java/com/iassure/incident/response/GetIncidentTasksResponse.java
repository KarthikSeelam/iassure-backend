package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.GetCapTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class GetIncidentTasksResponse {
    private StatusResponse statusResponse;
    private List<GetCapTask> taskListDetails;
    private List<DocumentEntity> taskFileDetails;
}
