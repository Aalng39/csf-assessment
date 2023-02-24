package vttp2022.csf.assessment.server.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String RESTAURANT_COL = "restaurants";

	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	// db.restaurants.distinct({ "cuisine" })
	public List<String> getCuisines() {
		// Implmementation in here
		List<String> cuisineList = mongoTemplate.findDistinct(
			new Query(), "cuisine", RESTAURANT_COL, String.class);

		List<String> cuisines = new LinkedList<>();

		for(String cuisine: cuisineList){
			System.out.println(cuisine.replaceAll( "/", "_"));
			cuisines.add(cuisine.replaceAll( "/", "_"));
		}

		return cuisines;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	// db.restaurants.distinct("name" , {cuisine: "Asian"} )
	// db.restaurants.find({ cuisine: "Asian" }, { _id: 0, name: 1, restaurant_id: 1 })
	public List<String> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		Criteria criterial = Criteria.where("cuisine").is(cuisine.replaceAll("_", "/"));
        Query query = Query.query(criterial);

		List<String> restList = mongoTemplate.findDistinct(
			query, "name", RESTAURANT_COL, String.class);

		

		return restList;
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method


	// db.restaurants.aggregate([
	// {
	// 	$match: {name: "Ajisen Ramen"}
	// },
	// {
	// 	$project : {_id:0, restaurant_id:1, name:1, cuisine: 1, address: { $concat: [ "$address.building"," ","$address.street", " ","$address.zipcode"]}, coordinates: "$address.coord"}
	// }])

	public Optional<Restaurant> getRestaurant(String name) {
		// Implmementation in here
		
        MatchOperation match = Aggregation.match(
                Criteria.where("name").is(name));

		;
				
        ProjectionOperation projection = Aggregation
                .project("restaurant_id", "name", "cuisine")
                .and(
					StringOperators.Concat.valueOf("address.building").concat(" ")
					.concatValueOf("address.street").concat(" ")
					.concatValueOf("address.zipcode")
					).as("address")
				.and("address.coord")
					.as("coordinates");

        Aggregation pipeline = Aggregation
                .newAggregation(match, projection);

        AggregationResults<Document> results = mongoTemplate
            .aggregate(pipeline, "restaurants", Document.class);
			
		if (!results.iterator().hasNext())
            return Optional.empty();

        Document d = results.iterator().next();
		Restaurant restaurant = Restaurant.create(d);
		System.out.println(restaurant);

		return Optional.of(restaurant);
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		
	}
	
	// You may add other methods to this class

}
