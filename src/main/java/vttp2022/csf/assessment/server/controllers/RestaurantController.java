package vttp2022.csf.assessment.server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping("/api")
public class RestaurantController {
    
    @Autowired
    private RestaurantService restSvc;
    
    @GetMapping(path="/cuisines", 
    produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getCuisines(){

        List<String> cuisines = restSvc.getCuisines();
        
        // for(String cuisine: cuisines){
        //     System.out.println(cuisine);
        // }
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        cuisines.stream()
                .forEach(cus -> {
                    arrBuilder.add(Json.createObjectBuilder()
                    .add("cuisine", cus)
                    .build());
                });

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/{cuisine}/restaurants", 
    produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getRestaurantByCuisine(@PathVariable String cuisine){

        List<String> restaurants = restSvc.getRestaurantsByCuisine(cuisine);
        
        // for(String cuisine: cuisines){
        //     System.out.println(cuisine);
        // }
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        restaurants.stream()
                .forEach(cus -> {
                    arrBuilder.add(Json.createObjectBuilder()
                    .add("name", cus)
                    .build());
                });

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/restaurants", 
    produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getRestaurantDetails(@RequestParam String restaurant){

        Optional<Restaurant> opt = restSvc.getRestaurant(restaurant.replaceAll("%", " "));
        
        if(opt.isEmpty()){
            JsonObject response = Json.createObjectBuilder()
			.add("message", "Restaurant deatils not found")
			.build();

            return ResponseEntity
            .status(404)
            .body(response.toString());

        }

        return ResponseEntity.ok(opt.get().toJson().toString());
    }
}
