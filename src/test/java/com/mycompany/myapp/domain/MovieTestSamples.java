package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Movie getMovieSample1() {
        return new Movie()
            .id("id1")
            .title("title1")
            .plot("plot1")
            .fullplot("fullplot1")
            .year(1)
            .runtime(1)
            .rated("rated1")
            .genres("genres1")
            .cast("cast1")
            .languages("languages1")
            .poster("poster1")
            .imdbVotes(1);
    }

    public static Movie getMovieSample2() {
        return new Movie()
            .id("id2")
            .title("title2")
            .plot("plot2")
            .fullplot("fullplot2")
            .year(2)
            .runtime(2)
            .rated("rated2")
            .genres("genres2")
            .cast("cast2")
            .languages("languages2")
            .poster("poster2")
            .imdbVotes(2);
    }

    public static Movie getMovieRandomSampleGenerator() {
        return new Movie()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .plot(UUID.randomUUID().toString())
            .fullplot(UUID.randomUUID().toString())
            .year(intCount.incrementAndGet())
            .runtime(intCount.incrementAndGet())
            .rated(UUID.randomUUID().toString())
            .genres(UUID.randomUUID().toString())
            .cast(UUID.randomUUID().toString())
            .languages(UUID.randomUUID().toString())
            .poster(UUID.randomUUID().toString())
            .imdbVotes(intCount.incrementAndGet());
    }
}
