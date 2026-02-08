package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.repository.MovieRepository;
import com.mycompany.myapp.service.MovieService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Movie}.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieResource {

    private static final Logger LOG = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movieCatalogMovie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieService movieService;

    private final MovieRepository movieRepository;

    public MovieResource(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    /**
     * {@code POST  /movies} : Create a new movie.
     *
     * @param movie the movie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movie, or with status {@code 400 (Bad Request)} if the movie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) throws URISyntaxException {
        LOG.debug("REST request to save Movie : {}", movie);
        if (movie.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movie = movieService.save(movie);
        return ResponseEntity.created(new URI("/api/movies/" + movie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, movie.getId()))
            .body(movie);
    }

    /**
     * {@code PUT  /movies/:id} : Updates an existing movie.
     *
     * @param id the id of the movie to save.
     * @param movie the movie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movie,
     * or with status {@code 400 (Bad Request)} if the movie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Movie movie
    ) throws URISyntaxException {
        LOG.debug("REST request to update Movie : {}, {}", id, movie);
        if (movie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movie = movieService.update(movie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movie.getId()))
            .body(movie);
    }

    /**
     * {@code PATCH  /movies/:id} : Partial updates given fields of an existing movie, field will ignore if it is null
     *
     * @param id the id of the movie to save.
     * @param movie the movie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movie,
     * or with status {@code 400 (Bad Request)} if the movie is not valid,
     * or with status {@code 404 (Not Found)} if the movie is not found,
     * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Movie> partialUpdateMovie(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Movie movie
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Movie partially : {}, {}", id, movie);
        if (movie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Movie> result = movieService.partialUpdate(movie);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movie.getId()));
    }

    /**
     * {@code GET  /movies} : get all the movies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Movie>> getAllMovies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Movies");
        Page<Movie> page = movieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movies/:id} : get the "id" movie.
     *
     * @param id the id of the movie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable("id") String id) {
        LOG.debug("REST request to get Movie : {}", id);
        Optional<Movie> movie = movieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movie);
    }

    /**
     * {@code DELETE  /movies/:id} : delete the "id" movie.
     *
     * @param id the id of the movie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Movie : {}", id);
        movieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
