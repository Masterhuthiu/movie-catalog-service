package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "movies")
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("plot")
    private String plot;

    @Field("fullplot")
    private String fullplot;

    @Field("year")
    private Integer year;

    @Field("runtime")
    private Integer runtime;

    @Field("rated")
    private String rated;

    @Field("released")
    private Instant released;

    @Field("poster")
    private String poster;

    @Field("genres")
    private Set<String> genres = new HashSet<>();

    @Field("cast")
    private Set<String> cast = new HashSet<>();

    @Field("languages")
    private Set<String> languages = new HashSet<>();

    @Field("imdb.rating")
    private Double imdbRating;

    @Field("imdb.votes")
    private Integer imdbVotes;

    // --- GETTERS & SETTERS TRUYỀN THỐNG ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }
    public String getFullplot() { return fullplot; }
    public void setFullplot(String fullplot) { this.fullplot = fullplot; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getRuntime() { return runtime; }
    public void setRuntime(Integer runtime) { this.runtime = runtime; }
    public String getRated() { return rated; }
    public void setRated(String rated) { this.rated = rated; }
    public Instant getReleased() { return released; }
    public void setReleased(Instant released) { this.released = released; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }
    public Set<String> getCast() { return cast; }
    public void setCast(Set<String> cast) { this.cast = cast; }
    public Set<String> getLanguages() { return languages; }
    public void setLanguages(Set<String> languages) { this.languages = languages; }
    public Double getImdbRating() { return imdbRating; }
    public void setImdbRating(Double imdbRating) { this.imdbRating = imdbRating; }
    public Integer getImdbVotes() { return imdbVotes; }
    public void setImdbVotes(Integer imdbVotes) { this.imdbVotes = imdbVotes; }

    // --- FLUENT METHODS (BẮT BUỘC ĐỂ FIX 8 LỖI TRONG LOG CỦA BẠN) ---
    public Movie id(String id) { this.setId(id); return this; }
    public Movie title(String title) { this.setTitle(title); return this; }
    public Movie plot(String plot) { this.setPlot(plot); return this; }
    public Movie fullplot(String fullplot) { this.setFullplot(fullplot); return this; }
    public Movie year(Integer year) { this.setYear(year); return this; }
    public Movie runtime(Integer runtime) { this.setRuntime(runtime); return this; }
    public Movie rated(String rated) { this.setRated(rated); return this; }
    public Movie released(Instant released) { this.setReleased(released); return this; }
    public Movie poster(String poster) { this.setPoster(poster); return this; }
    public Movie imdbRating(Double imdbRating) { this.setImdbRating(imdbRating); return this; }
    public Movie imdbVotes(Integer imdbVotes) { this.setImdbVotes(imdbVotes); return this; }

    // Fluent methods cho các trường kiểu Set (genres, cast, languages)
    public Movie genres(String genres) {
        this.genres.add(genres);
        return this;
    }
    public Movie cast(String cast) {
        this.cast.add(cast);
        return this;
    }
    public Movie languages(String languages) {
        this.languages.add(languages);
        return this;
    }
}
// package com.mycompany.myapp.domain;

// import jakarta.validation.constraints.*;
// import java.io.Serializable;
// import java.time.Instant;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.Field;

// /**
//  * A Movie.
//  */
// @Document(collection = "movies")
// @SuppressWarnings("common-java:DuplicatedBlocks")
// public class Movie implements Serializable {

//     private static final long serialVersionUID = 1L;

//     @Id
//     private String id;

//     @NotNull
//     @Field("title")
//     private String title;

//     @Field("plot")
//     private String plot;

//     @Field("fullplot")
//     private String fullplot;

//     @Field("year")
//     private Integer year;

//     @Field("runtime")
//     private Integer runtime;

//     @Field("rated")
//     private String rated;

//     @Field("released")
//     private Instant released;

//     @Field("genres")
//     private String genres;

//     @Field("cast")
//     private String cast;

//     @Field("languages")
//     private String languages;

//     @Field("poster")
//     private String poster;

//     @Field("imdb_rating")
//     private Double imdbRating;

//     @Field("imdb_votes")
//     private Integer imdbVotes;

//     // jhipster-needle-entity-add-field - JHipster will add fields here

//     public String getId() {
//         return this.id;
//     }

//     public Movie id(String id) {
//         this.setId(id);
//         return this;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getTitle() {
//         return this.title;
//     }

//     public Movie title(String title) {
//         this.setTitle(title);
//         return this;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public String getPlot() {
//         return this.plot;
//     }

//     public Movie plot(String plot) {
//         this.setPlot(plot);
//         return this;
//     }

//     public void setPlot(String plot) {
//         this.plot = plot;
//     }

//     public String getFullplot() {
//         return this.fullplot;
//     }

//     public Movie fullplot(String fullplot) {
//         this.setFullplot(fullplot);
//         return this;
//     }

//     public void setFullplot(String fullplot) {
//         this.fullplot = fullplot;
//     }

//     public Integer getYear() {
//         return this.year;
//     }

//     public Movie year(Integer year) {
//         this.setYear(year);
//         return this;
//     }

//     public void setYear(Integer year) {
//         this.year = year;
//     }

//     public Integer getRuntime() {
//         return this.runtime;
//     }

//     public Movie runtime(Integer runtime) {
//         this.setRuntime(runtime);
//         return this;
//     }

//     public void setRuntime(Integer runtime) {
//         this.runtime = runtime;
//     }

//     public String getRated() {
//         return this.rated;
//     }

//     public Movie rated(String rated) {
//         this.setRated(rated);
//         return this;
//     }

//     public void setRated(String rated) {
//         this.rated = rated;
//     }

//     public Instant getReleased() {
//         return this.released;
//     }

//     public Movie released(Instant released) {
//         this.setReleased(released);
//         return this;
//     }

//     public void setReleased(Instant released) {
//         this.released = released;
//     }

//     public String getGenres() {
//         return this.genres;
//     }

//     public Movie genres(String genres) {
//         this.setGenres(genres);
//         return this;
//     }

//     public void setGenres(String genres) {
//         this.genres = genres;
//     }

//     public String getCast() {
//         return this.cast;
//     }

//     public Movie cast(String cast) {
//         this.setCast(cast);
//         return this;
//     }

//     public void setCast(String cast) {
//         this.cast = cast;
//     }

//     public String getLanguages() {
//         return this.languages;
//     }

//     public Movie languages(String languages) {
//         this.setLanguages(languages);
//         return this;
//     }

//     public void setLanguages(String languages) {
//         this.languages = languages;
//     }

//     public String getPoster() {
//         return this.poster;
//     }

//     public Movie poster(String poster) {
//         this.setPoster(poster);
//         return this;
//     }

//     public void setPoster(String poster) {
//         this.poster = poster;
//     }

//     public Double getImdbRating() {
//         return this.imdbRating;
//     }

//     public Movie imdbRating(Double imdbRating) {
//         this.setImdbRating(imdbRating);
//         return this;
//     }

//     public void setImdbRating(Double imdbRating) {
//         this.imdbRating = imdbRating;
//     }

//     public Integer getImdbVotes() {
//         return this.imdbVotes;
//     }

//     public Movie imdbVotes(Integer imdbVotes) {
//         this.setImdbVotes(imdbVotes);
//         return this;
//     }

//     public void setImdbVotes(Integer imdbVotes) {
//         this.imdbVotes = imdbVotes;
//     }

//     // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) {
//             return true;
//         }
//         if (!(o instanceof Movie)) {
//             return false;
//         }
//         return getId() != null && getId().equals(((Movie) o).getId());
//     }

//     @Override
//     public int hashCode() {
//         // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
//         return getClass().hashCode();
//     }

//     // prettier-ignore
//     @Override
//     public String toString() {
//         return "Movie{" +
//             "id=" + getId() +
//             ", title='" + getTitle() + "'" +
//             ", plot='" + getPlot() + "'" +
//             ", fullplot='" + getFullplot() + "'" +
//             ", year=" + getYear() +
//             ", runtime=" + getRuntime() +
//             ", rated='" + getRated() + "'" +
//             ", released='" + getReleased() + "'" +
//             ", genres='" + getGenres() + "'" +
//             ", cast='" + getCast() + "'" +
//             ", languages='" + getLanguages() + "'" +
//             ", poster='" + getPoster() + "'" +
//             ", imdbRating=" + getImdbRating() +
//             ", imdbVotes=" + getImdbVotes() +
//             "}";
//     }
// }
