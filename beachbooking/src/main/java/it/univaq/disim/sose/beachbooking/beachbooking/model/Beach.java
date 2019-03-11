package it.univaq.disim.sose.beachbooking.beachbooking.model;

import java.util.List;

@SuppressWarnings("serial")
public class Beach implements java.io.Serializable {

	private Long id;
	private String name;
	private double price;
	private int rating;
	private String city;
	private int zone;
	private List<Parking> nearParkings;

	public List<Parking> getNearParkings() {
		return nearParkings;
	}

	public void setNearParkings(List<Parking> nearParkings) {
		this.nearParkings = nearParkings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

}
