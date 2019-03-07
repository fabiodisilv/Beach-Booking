package it.univaq.disim.sose.beachbooking.beachbooking.model;

import java.sql.Date;

@SuppressWarnings("serial")
public class Booking {

	private Long id;
	private Long beachId;
	private Date date;
	private Boolean canceled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBeachId() {
		return beachId;
	}

	public void setBeachId(Long beachId) {
		this.beachId = beachId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

}
