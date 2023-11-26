package dev.josue.movies;

import dev.josue.movies.models.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public abstract class CrudOpMovies {
    public abstract ResponseEntity<List<Movie>> getMovies();
    public abstract ResponseEntity<Optional<Movie>>  getSingleMovie(@PathVariable String imdbId);
    public abstract ResponseEntity<Movie> createMovie(@RequestBody Movie movie);
    public abstract ResponseEntity<Movie> updateMovie(@PathVariable String imdbId, @RequestBody Movie movie);
    public abstract ResponseEntity<String> deleteMovie(@PathVariable("imdbId")String imdbId);
}
