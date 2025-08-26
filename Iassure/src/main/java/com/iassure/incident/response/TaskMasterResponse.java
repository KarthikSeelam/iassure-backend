package com.iassure.incident.response;

import com.iassure.incident.entity.TaskMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class TaskMasterResponse {
    private StatusResponse statusResponse;
    private TaskMaster tasks;
}
