package dev.josue.movies.services;

import dev.josue.movies.controllers.MovieController;
import dev.josue.movies.db.MovieRepository;
import dev.josue.movies.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }
    public Optional<Movie> getOneMovie(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public Movie createMovie(Movie movie) {
       return movieRepository.save(movie);
    }

    public Movie updateMovie(String imdbId, Movie movie){
        if(movieRepository.existsByImdbId(imdbId)){
            movie.setImdbId(imdbId);
            return movieRepository.save(movie);
        } else {
            return null;
        }
    }

    public boolean deleteMovieByImdbId(String imdbId){
        if(movieRepository.existsByImdbId(imdbId)){
            movieRepository.deleteByImdbId(imdbId);
            return true;
        }else{
            return false;
        }
    }

}
