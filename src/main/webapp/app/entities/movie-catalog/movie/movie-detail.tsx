import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './movie.reducer';

export const MovieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const movieEntity = useAppSelector(state => state.moviecatalogservice.movie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="movieDetailsHeading">
          <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.detail.title">Movie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{movieEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.title">Title</Translate>
            </span>
          </dt>
          <dd>{movieEntity.title}</dd>
          <dt>
            <span id="plot">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.plot">Plot</Translate>
            </span>
          </dt>
          <dd>{movieEntity.plot}</dd>
          <dt>
            <span id="fullplot">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.fullplot">Fullplot</Translate>
            </span>
          </dt>
          <dd>{movieEntity.fullplot}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.year">Year</Translate>
            </span>
          </dt>
          <dd>{movieEntity.year}</dd>
          <dt>
            <span id="runtime">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.runtime">Runtime</Translate>
            </span>
          </dt>
          <dd>{movieEntity.runtime}</dd>
          <dt>
            <span id="rated">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.rated">Rated</Translate>
            </span>
          </dt>
          <dd>{movieEntity.rated}</dd>
          <dt>
            <span id="released">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.released">Released</Translate>
            </span>
          </dt>
          <dd>{movieEntity.released ? <TextFormat value={movieEntity.released} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="genres">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.genres">Genres</Translate>
            </span>
          </dt>
          <dd>{movieEntity.genres}</dd>
          <dt>
            <span id="cast">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.cast">Cast</Translate>
            </span>
          </dt>
          <dd>{movieEntity.cast}</dd>
          <dt>
            <span id="languages">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.languages">Languages</Translate>
            </span>
          </dt>
          <dd>{movieEntity.languages}</dd>
          <dt>
            <span id="poster">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.poster">Poster</Translate>
            </span>
          </dt>
          <dd>{movieEntity.poster}</dd>
          <dt>
            <span id="imdbRating">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.imdbRating">Imdb Rating</Translate>
            </span>
          </dt>
          <dd>{movieEntity.imdbRating}</dd>
          <dt>
            <span id="imdbVotes">
              <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.imdbVotes">Imdb Votes</Translate>
            </span>
          </dt>
          <dd>{movieEntity.imdbVotes}</dd>
        </dl>
        <Button tag={Link} to="/movie-catalog/movie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-catalog/movie/${movieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MovieDetail;
