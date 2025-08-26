package com.iassure.incident.response;

import com.iassure.incident.entity.TaskMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class FetchTaskResponse {
    private StatusResponse statusResponse;
    private List<TaskMaster> tasks;
}
