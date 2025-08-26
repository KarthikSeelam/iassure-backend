package com.iassure.constants;

/**
 * 
 * @author Naveen kumar chintala
 *
 */
public enum IncidentStatus {
	
	OPEN(1, "Open"),
	INPROGRESS(2, "In Progress"),
	CLOSE(3, "Close"),
	OVERDUE(4, "Over Due");
	
	private Integer id;
	private String status;
	
	 IncidentStatus(Integer id, String status) {
		this.id = id;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	 

}
