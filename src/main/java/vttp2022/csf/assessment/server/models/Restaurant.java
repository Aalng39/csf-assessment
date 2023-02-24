package vttp2022.csf.assessment.server.models;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// Do not modify this class
public class Restaurant {
	
	private String restaurantId;
	private String name;
	private String cuisine;
	private String address;
	private LatLng coordinates;
	private String mapUrl;

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantId() {
		return this.restaurantId;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public String getCuisine() {
		return this.cuisine;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	public LatLng getCoordinates() {
		return this.coordinates;
	}

	public void setMapURL(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getMapURL() {
		return this.mapUrl;
	}

	public static Restaurant create(Document d){

        Restaurant rest = new Restaurant();
        rest.setRestaurantId(d.getString("restaurant_id"));
		rest.setName(d.getString("name"));
		rest.setCuisine(d.getString("cuisine"));
		rest.setAddress(d.getString("address"));
        
        List<Double> list = d.get("coordinates", List.class);
            
		rest.setCoordinates(LatLng.create(list));
        
        return rest;
    }

	public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("restaurant_id", getRestaurantId())
            .add("name", getName())
            .add("cuisine", getCuisine())
            .add("address", getAddress())
			.add("coordinates", getCoordinates().toString() )
            .build();
    }
}
