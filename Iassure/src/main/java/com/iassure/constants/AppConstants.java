package com.iassure.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstants {
	
	public static String SERVER_CONTEXT_PATH = null;
	
	
	
	
	@Value(value="${server.servlet.context-path}")
	public void setAwsAccessKey(String serverContextPath) {
		AppConstants.SERVER_CONTEXT_PATH = serverContextPath;
	}
	
	public static int HIGH_PRIORITY_DAYS = 1;
	public static int MEDIUM_PRIORITY_DAYS = 7;
	public static int LOW_PRIORITY_DAYS = 30;
	
	//INCIDENT CASE STATUS
	public static String IN_PROCESS = "IN PROCESS";
	public static String OVER_DUE = "OVER DUE";
	public static String TODAY_HIGH_PRORITY = "TODAY";
	public static String  RESOLVED = "RESOLVED";
	
	//INCIDENT PRIORITY
	public static int HIGH_PRIORITY = 1;
	public static int MEDIUM_PRIORITY = 2;
	public static int LOW_PRIORITY = 3;
	public static final String HIGH_PRIORITY_STRING = "Critical";
	public static final String MEDIUM_PRIORITY_STRING = "Major";
	public static final String LOW_PRIORITY_STRING = "Minor";
	public static final String IN_VALID_USER_MSG = "Please Enter valid user name";
	
	public static final String NO_RECORDS = "No Records";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String INVALID_PARAMETERS = "Invalid Parameters";
	//Incident Notification Management	
	public static final String INCIDENT_NOTIFICATION_FOR_ADMIN = "Incident raised by ";
	public static final String INCIDENT_MODULE_NAME = "Incident";
	public static final String NOTIFICATION_TYPE_GENERAL = "General";
	public static final String INCIDEN_NOTIFICATION_MESSAGE_FOR_ADMIN = "New Incident Raised!!";
	public static final String TRAINIG_ALERT_NOTIFICATION_MESSAGE_FOR_ADMIN = "Training Scheduled Today";
	public static final String INCIDENT_RESOLVE_NOTIFICATION_URL = "incidents/resolve?id=";
	public static final String INCIDENT_NOTIFICATION_FOR_RESOLVE_USER = "Incident Resolved";
	public static final String SUCCESS_NOTIFICATION = "SUCCESS";
	public static final String USER_INCIDENT_LIST_URL = "incidents/userIncidentHistory";
	public static final String INCIDENT_RESOLVE_MESSAGE_DESC = "Your incident resolved with @@incidentid@@";
	public static final String CLOSED = "Closed";
	public static final String OPEN = "Open";
	
	public static final String USER_TRAINING_NOTIFICATION_URL = "training/view?id=@@ID&excutionId=";
	
	//FILE IN FOR
	public static final String REPOSITARY_FILE_SEPARATION_STRING = "$";
	
	public static final String SUFFIX = "/";
	
	//TRAINING MESSAGE
	public static final String TRAINING_FAILED_MESSAGE = "Sorry,you did not score more than 95% in the test";
	public static final String TRAINING_SUCCESS_MESSAGE = "Congratulations! You have passed in the test";

	
	
	
	

}
