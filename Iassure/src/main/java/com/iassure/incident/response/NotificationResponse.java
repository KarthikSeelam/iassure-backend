package com.iassure.incident.response;

import com.iassure.incident.entity.IncidentHistory;
import com.iassure.incident.entity.IncidentNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class NotificationResponse {
    private StatusResponse statusResponse;
    private int totalCount;
    private List<IncidentNotification> notificationsList;
}
