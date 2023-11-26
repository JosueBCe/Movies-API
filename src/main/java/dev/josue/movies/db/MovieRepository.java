package dev.josue.movies.db;

import dev.josue.movies.models.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId>
{
    // Use another value for being the ID, instead of mongodb id
    Optional<Movie> findMovieByImdbId(String  imdbId);

    boolean existsByImdbId(String imdbId);
    void deleteByImdbId(String imdbId);

}
