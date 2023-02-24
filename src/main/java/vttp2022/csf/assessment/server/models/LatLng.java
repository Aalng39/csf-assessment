package vttp2022.csf.assessment.server.models;

import java.util.List;

// Do not modify this class
public class LatLng {
	private float latitude;
	private float longitude;

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLatitude() {
		return this.latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLongitude() {
		return this.longitude;
	}

	public static LatLng create(List<Double> list){
		
		LatLng latLng = new LatLng();
		latLng.setLongitude((list.get(0)).floatValue());
		latLng.setLatitude((list.get(1)).floatValue());
		
        return latLng;
    }
}
