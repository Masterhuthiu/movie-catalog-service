package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Movie entity.
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {}
