package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.repository.MovieRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Movie}.
 */
@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Save a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    public Movie save(Movie movie) {
        LOG.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Update a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    public Movie update(Movie movie) {
        LOG.debug("Request to update Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Partially update a movie.
     *
     * @param movie the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Movie> partialUpdate(Movie movie) {
        LOG.debug("Request to partially update Movie : {}", movie);

        return movieRepository
            .findById(movie.getId())
            .map(existingMovie -> {
                if (movie.getTitle() != null) {
                    existingMovie.setTitle(movie.getTitle());
                }
                if (movie.getPlot() != null) {
                    existingMovie.setPlot(movie.getPlot());
                }
                if (movie.getFullplot() != null) {
                    existingMovie.setFullplot(movie.getFullplot());
                }
                if (movie.getYear() != null) {
                    existingMovie.setYear(movie.getYear());
                }
                if (movie.getRuntime() != null) {
                    existingMovie.setRuntime(movie.getRuntime());
                }
                if (movie.getRated() != null) {
                    existingMovie.setRated(movie.getRated());
                }
                if (movie.getReleased() != null) {
                    existingMovie.setReleased(movie.getReleased());
                }
                if (movie.getGenres() != null) {
                    existingMovie.setGenres(movie.getGenres());
                }
                if (movie.getCast() != null) {
                    existingMovie.setCast(movie.getCast());
                }
                if (movie.getLanguages() != null) {
                    existingMovie.setLanguages(movie.getLanguages());
                }
                if (movie.getPoster() != null) {
                    existingMovie.setPoster(movie.getPoster());
                }
                if (movie.getImdbRating() != null) {
                    existingMovie.setImdbRating(movie.getImdbRating());
                }
                if (movie.getImdbVotes() != null) {
                    existingMovie.setImdbVotes(movie.getImdbVotes());
                }

                return existingMovie;
            })
            .map(movieRepository::save);
    }

    /**
     * Get all the movies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Movie> findAll(Pageable pageable) {
        LOG.debug("Request to get all Movies");
        return movieRepository.findAll(pageable);
    }

    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Movie> findOne(String id) {
        LOG.debug("Request to get Movie : {}", id);
        return movieRepository.findById(id);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
