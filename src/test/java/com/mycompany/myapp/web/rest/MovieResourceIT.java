package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MovieAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.repository.MovieRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link MovieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PLOT = "AAAAAAAAAA";
    private static final String UPDATED_PLOT = "BBBBBBBBBB";

    private static final String DEFAULT_FULLPLOT = "AAAAAAAAAA";
    private static final String UPDATED_FULLPLOT = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_RUNTIME = 1;
    private static final Integer UPDATED_RUNTIME = 2;

    private static final String DEFAULT_RATED = "AAAAAAAAAA";
    private static final String UPDATED_RATED = "BBBBBBBBBB";

    private static final Instant DEFAULT_RELEASED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GENRES = "AAAAAAAAAA";
    private static final String UPDATED_GENRES = "BBBBBBBBBB";

    private static final String DEFAULT_CAST = "AAAAAAAAAA";
    private static final String UPDATED_CAST = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_POSTER = "AAAAAAAAAA";
    private static final String UPDATED_POSTER = "BBBBBBBBBB";

    private static final Double DEFAULT_IMDB_RATING = 1D;
    private static final Double UPDATED_IMDB_RATING = 2D;

    private static final Integer DEFAULT_IMDB_VOTES = 1;
    private static final Integer UPDATED_IMDB_VOTES = 2;

    private static final String ENTITY_API_URL = "/api/movies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;

    private Movie insertedMovie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity() {
        return new Movie()
            .title(DEFAULT_TITLE)
            .plot(DEFAULT_PLOT)
            .fullplot(DEFAULT_FULLPLOT)
            .year(DEFAULT_YEAR)
            .runtime(DEFAULT_RUNTIME)
            .rated(DEFAULT_RATED)
            .released(DEFAULT_RELEASED)
            .genres(DEFAULT_GENRES)
            .cast(DEFAULT_CAST)
            .languages(DEFAULT_LANGUAGES)
            .poster(DEFAULT_POSTER)
            .imdbRating(DEFAULT_IMDB_RATING)
            .imdbVotes(DEFAULT_IMDB_VOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity() {
        return new Movie()
            .title(UPDATED_TITLE)
            .plot(UPDATED_PLOT)
            .fullplot(UPDATED_FULLPLOT)
            .year(UPDATED_YEAR)
            .runtime(UPDATED_RUNTIME)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .genres(UPDATED_GENRES)
            .cast(UPDATED_CAST)
            .languages(UPDATED_LANGUAGES)
            .poster(UPDATED_POSTER)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES);
    }

    @BeforeEach
    void initTest() {
        movie = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMovie != null) {
            movieRepository.delete(insertedMovie);
            insertedMovie = null;
        }
    }

    @Test
    void createMovie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Movie
        var returnedMovie = om.readValue(
            restMovieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Movie.class
        );

        // Validate the Movie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMovieUpdatableFieldsEquals(returnedMovie, getPersistedMovie(returnedMovie));

        insertedMovie = returnedMovie;
    }

    @Test
    void createMovieWithExistingId() throws Exception {
        // Create the Movie with an existing ID
        movie.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setTitle(null);

        // Create the Movie, which fails.

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMovies() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        // Get all the movieList
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].plot").value(hasItem(DEFAULT_PLOT)))
            .andExpect(jsonPath("$.[*].fullplot").value(hasItem(DEFAULT_FULLPLOT)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].runtime").value(hasItem(DEFAULT_RUNTIME)))
            .andExpect(jsonPath("$.[*].rated").value(hasItem(DEFAULT_RATED)))
            .andExpect(jsonPath("$.[*].released").value(hasItem(DEFAULT_RELEASED.toString())))
            .andExpect(jsonPath("$.[*].genres").value(hasItem(DEFAULT_GENRES)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].poster").value(hasItem(DEFAULT_POSTER)))
            .andExpect(jsonPath("$.[*].imdbRating").value(hasItem(DEFAULT_IMDB_RATING)))
            .andExpect(jsonPath("$.[*].imdbVotes").value(hasItem(DEFAULT_IMDB_VOTES)));
    }

    @Test
    void getMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        // Get the movie
        restMovieMockMvc
            .perform(get(ENTITY_API_URL_ID, movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.plot").value(DEFAULT_PLOT))
            .andExpect(jsonPath("$.fullplot").value(DEFAULT_FULLPLOT))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.runtime").value(DEFAULT_RUNTIME))
            .andExpect(jsonPath("$.rated").value(DEFAULT_RATED))
            .andExpect(jsonPath("$.released").value(DEFAULT_RELEASED.toString()))
            .andExpect(jsonPath("$.genres").value(DEFAULT_GENRES))
            .andExpect(jsonPath("$.cast").value(DEFAULT_CAST))
            .andExpect(jsonPath("$.languages").value(DEFAULT_LANGUAGES))
            .andExpect(jsonPath("$.poster").value(DEFAULT_POSTER))
            .andExpect(jsonPath("$.imdbRating").value(DEFAULT_IMDB_RATING))
            .andExpect(jsonPath("$.imdbVotes").value(DEFAULT_IMDB_VOTES));
    }

    @Test
    void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).orElseThrow();
        updatedMovie
            .title(UPDATED_TITLE)
            .plot(UPDATED_PLOT)
            .fullplot(UPDATED_FULLPLOT)
            .year(UPDATED_YEAR)
            .runtime(UPDATED_RUNTIME)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .genres(UPDATED_GENRES)
            .cast(UPDATED_CAST)
            .languages(UPDATED_LANGUAGES)
            .poster(UPDATED_POSTER)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES);

        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMovie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovieToMatchAllProperties(updatedMovie);
    }

    @Test
    void putNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL_ID, movie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .plot(UPDATED_PLOT)
            .fullplot(UPDATED_FULLPLOT)
            .year(UPDATED_YEAR)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .imdbVotes(UPDATED_IMDB_VOTES);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMovie, movie), getPersistedMovie(movie));
    }

    @Test
    void fullUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .title(UPDATED_TITLE)
            .plot(UPDATED_PLOT)
            .fullplot(UPDATED_FULLPLOT)
            .year(UPDATED_YEAR)
            .runtime(UPDATED_RUNTIME)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .genres(UPDATED_GENRES)
            .cast(UPDATED_CAST)
            .languages(UPDATED_LANGUAGES)
            .poster(UPDATED_POSTER)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(partialUpdatedMovie, getPersistedMovie(partialUpdatedMovie));
    }

    @Test
    void patchNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.save(movie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movie
        restMovieMockMvc
            .perform(delete(ENTITY_API_URL_ID, movie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movieRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Movie getPersistedMovie(Movie movie) {
        return movieRepository.findById(movie.getId()).orElseThrow();
    }

    protected void assertPersistedMovieToMatchAllProperties(Movie expectedMovie) {
        assertMovieAllPropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }

    protected void assertPersistedMovieToMatchUpdatableProperties(Movie expectedMovie) {
        assertMovieAllUpdatablePropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }
}
