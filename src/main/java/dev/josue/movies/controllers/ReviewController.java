package dev.josue.movies.controllers;

import dev.josue.movies.CrudOpReviews;
import dev.josue.movies.caching.CachedMovie;
import dev.josue.movies.models.Movie;
import dev.josue.movies.services.ReviewService;
import dev.josue.movies.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static dev.josue.movies.controllers.MovieController.cacheKeyForMovies;

@RestController
@CrossOrigin(origins = "*") // Allow To Do request from any server: for testing purposes in PostMan
@RequestMapping("/api/v1/reviews")
public class ReviewController implements CrudOpReviews {

    @Autowired
    private ReviewService reviewService;

    // General movies cache
    private CachedMovie cachedMovie;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        try {
            return new ResponseEntity<List<Review>>(reviewService.getAllReviews(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {

        try{
            // Delete cache to have the last update
            cachedMovie = CachedMovie.getInstance();
            cachedMovie.removeMoviesFromCache(cacheKeyForMovies);
            return new ResponseEntity<Review>(reviewService.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
