package dev.josue.movies;

import dev.josue.movies.models.Movie;
import dev.josue.movies.models.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public  interface CrudOpReviews {
    ResponseEntity<Review> createReview(@RequestBody Map<String,String> payload);

    ResponseEntity<List<Review>> getReviews();
}
