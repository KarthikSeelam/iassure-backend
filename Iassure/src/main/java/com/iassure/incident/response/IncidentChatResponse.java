package com.iassure.incident.response;

import com.iassure.incident.entity.GetIncidentChatDetails;
import com.iassure.incident.entity.IncidentChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class IncidentChatResponse {
    private StatusResponse statusResponse;
    private List<GetIncidentChatDetails> incidentChatsList;
    private IncidentChat incidentChat;
}
