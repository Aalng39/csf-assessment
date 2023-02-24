package vttp2022.csf.assessment.server.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.csf.assessment.server.models.LatLng;

@Repository
public class MapCache {

	public static final String URL_ENDPOINT = "http://map.chuklee.com/map";
	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public Byte getMap(LatLng latLng) {
		// Implmementation in here
		
		String url = UriComponentsBuilder.fromUriString(URL_ENDPOINT)
            .queryParam("lat", latLng.getLatitude())
            .queryParam("lng", latLng.getLongitude())
            .toUriString();
			
		RestTemplate template = new RestTemplate();
      

        ResponseEntity<String> resp =  template.getForEntity(url, String.class);
        String image = resp.getBody();
		byte[] bytes =image.getBytes();

		return null;
	}

	// You may add other methods to this class

}
