package it.univaq.disim.sose.beachbooking.beachbooking.model;

import java.util.List;

public class BeachInfoParking {

	private Long id;
	private String name;
	private Double price;
	private int rating;
	private String city;
	private int zone;
	private List<Parking> nearParkings;

	public Long getId() {
		return id;
	}

	public void setId(Long ind) {
		this.id = ind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

	public List<Parking> getNearParkings() {
		return nearParkings;
	}

	public void setNearParkings(List<Parking> nearParkings) {
		this.nearParkings = nearParkings;
	}

}
