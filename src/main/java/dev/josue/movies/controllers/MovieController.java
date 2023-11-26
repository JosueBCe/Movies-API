package dev.josue.movies.controllers;

import dev.josue.movies.CrudOpMovies;
import dev.josue.movies.caching.CachedMovie;
import dev.josue.movies.services.MovieService;
import dev.josue.movies.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
public class MovieController extends CrudOpMovies {
    @Autowired // It's necessary to initiate the class, that's the reason for adding the autowired
    private MovieService movieService;
    public final CachedMovie cachedMovie;
    public static String cacheKeyForMovies = "allMovies";

    public MovieController() {
        this.cachedMovie = CachedMovie.getInstance();
    }

    @GetMapping
    @Override
    public ResponseEntity<List<Movie>> getMovies() {
        try {
            List<Movie> movies = (List<Movie>) cachedMovie.getAllMoviesFromCache(cacheKeyForMovies);
            if (movies != null) {
                System.out.println("Got from the cache");
                return new ResponseEntity<>(movies, HttpStatus.OK);
            }

            movies = movieService.findAllMovies();
            // Adding movies to cache
            cachedMovie.addAllMoviesToCache(cacheKeyForMovies, movies);
            System.out.println("Got from the DB");

            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{imdbId}")
    @Override
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        try {
            return new ResponseEntity<Optional<Movie>>(movieService.getOneMovie(imdbId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    @Override
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        try {
            Movie movieCreated = movieService.createMovie(movie);

            // If the movie was successfully added, update the cache
            if (movieCreated != null) {

                // Get the current movies from the cache
                List<Movie> cachedMovies = (List<Movie>) cachedMovie.getAllMoviesFromCache(cacheKeyForMovies);

                // If the movies are present in the cache, add the new movie and update the cache
                if (cachedMovies != null) {
                    cachedMovies.add(movieCreated);
                    cachedMovie.addAllMoviesToCache(cacheKeyForMovies, cachedMovies);
                    System.out.println("Added movie to cache.");
                }
            }

            return new ResponseEntity<>(movieCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{imdbId}")
    @Override
    public ResponseEntity<Movie> updateMovie(@PathVariable("imdbId") String imdbId, @RequestBody Movie movie) {
        try {
            Movie updatedMovie = movieService.updateMovie(imdbId, movie);
            if (updatedMovie != null) {
                // Remove the cache if the database is updated
                cachedMovie.removeMoviesFromCache(cacheKeyForMovies);
                return new ResponseEntity<>(updatedMovie, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{imdbId}")
    @Override
    public ResponseEntity<String> deleteMovie(@PathVariable("imdbId") String imdbId) {
        try {
            boolean movieDeleted = movieService.deleteMovieByImdbId(imdbId);
            if (movieDeleted) {
                String successMessage = "Movie imdbId: " + imdbId + " Deleted Successfully";
                return new ResponseEntity<String>(successMessage, HttpStatus.OK);
            } else {
                String errorMessage = "Movie with IMDb ID " + imdbId + " not found.";
                return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
