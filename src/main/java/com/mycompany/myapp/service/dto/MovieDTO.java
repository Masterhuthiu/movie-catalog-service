package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class MovieDTO implements Serializable {
    private String id;
    private String title;
    private String plot;
    private String fullplot;
    private Integer year;
    private Integer runtime;
    private String rated;
    private String poster;
    private Instant released;
    private Set<String> genres = new HashSet<>();
    private Set<String> cast = new HashSet<>();
    private Set<String> languages = new HashSet<>();
    private Double imdbRating;
    private Integer imdbVotes;

    // --- GETTERS & SETTERS ---
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
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public Instant getReleased() { return released; }
    public void setReleased(Instant released) { this.released = released; }
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
}
