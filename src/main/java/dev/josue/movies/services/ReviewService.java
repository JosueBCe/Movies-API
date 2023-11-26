package dev.josue.movies.services;

import dev.josue.movies.db.ReviewRepository;
import dev.josue.movies.models.Movie;
import dev.josue.movies.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReviewService {
    @Autowired
    // Reference to the database
    private ReviewRepository reviewRepo;

    @Autowired
    // Another way to talk to the database
    private MongoTemplate mongoTemplate;

    public Review createReview(String review, String imdbId){
        Review reviewReturned = reviewRepo.insert(new Review(review));

        // Associate to movies

        mongoTemplate.update(Movie.class)
                                        // Column name in the database
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewsIds").value( reviewReturned))
                .first();
    return  reviewReturned;
    }
    public List<Review> getAllReviews() {
        List<Review> allReviews = reviewRepo.findAll();

        Set<Review> uniqueReviews = new HashSet<Review>(allReviews);

        return new ArrayList<>(uniqueReviews);
    }


}
