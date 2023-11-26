package dev.josue.movies.caching;

import java.util.HashMap;
import java.util.Map;

public class CachedMovie {
    // Implementation of Hashmap concept adding cache functionality
    private static CachedMovie instance;
    private Map<String, Object> cache;
    // Constructor
    public CachedMovie(){
        this.cache = new HashMap<>();
    }

    // Manage the instance of the cache in the app
    public static synchronized CachedMovie getInstance(){
        if (instance == null){
            instance = new CachedMovie();
        }
        return instance;
    }

    public void addAllMoviesToCache(String key, Object movies){
        cache.put(key, movies);
    }
    public Object getAllMoviesFromCache(String key){
        return  cache.get(key);
    }

    public void removeMoviesFromCache(String key){
        cache.remove(key);
        System.out.println("Success: Movies deleted from the cache.");
    }

}
