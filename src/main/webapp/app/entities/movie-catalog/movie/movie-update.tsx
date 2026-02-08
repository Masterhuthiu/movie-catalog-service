import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './movie.reducer';

export const MovieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const movieEntity = useAppSelector(state => state.moviecatalogservice.movie.entity);
  const loading = useAppSelector(state => state.moviecatalogservice.movie.loading);
  const updating = useAppSelector(state => state.moviecatalogservice.movie.updating);
  const updateSuccess = useAppSelector(state => state.moviecatalogservice.movie.updateSuccess);

  const handleClose = () => {
    navigate(`/movie-catalog/movie${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.year !== undefined && typeof values.year !== 'number') {
      values.year = Number(values.year);
    }
    if (values.runtime !== undefined && typeof values.runtime !== 'number') {
      values.runtime = Number(values.runtime);
    }
    values.released = convertDateTimeToServer(values.released);
    if (values.imdbRating !== undefined && typeof values.imdbRating !== 'number') {
      values.imdbRating = Number(values.imdbRating);
    }
    if (values.imdbVotes !== undefined && typeof values.imdbVotes !== 'number') {
      values.imdbVotes = Number(values.imdbVotes);
    }

    const entity = {
      ...movieEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          released: displayDefaultDateTime(),
        }
      : {
          ...movieEntity,
          released: convertDateTimeFromServer(movieEntity.released),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieCatalogServiceApp.movieCatalogMovie.home.createOrEditLabel" data-cy="MovieCreateUpdateHeading">
            <Translate contentKey="movieCatalogServiceApp.movieCatalogMovie.home.createOrEditLabel">Create or edit a Movie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="movie-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.title')}
                id="movie-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.plot')}
                id="movie-plot"
                name="plot"
                data-cy="plot"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.fullplot')}
                id="movie-fullplot"
                name="fullplot"
                data-cy="fullplot"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.year')}
                id="movie-year"
                name="year"
                data-cy="year"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.runtime')}
                id="movie-runtime"
                name="runtime"
                data-cy="runtime"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.rated')}
                id="movie-rated"
                name="rated"
                data-cy="rated"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.released')}
                id="movie-released"
                name="released"
                data-cy="released"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.genres')}
                id="movie-genres"
                name="genres"
                data-cy="genres"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.cast')}
                id="movie-cast"
                name="cast"
                data-cy="cast"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.languages')}
                id="movie-languages"
                name="languages"
                data-cy="languages"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.poster')}
                id="movie-poster"
                name="poster"
                data-cy="poster"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.imdbRating')}
                id="movie-imdbRating"
                name="imdbRating"
                data-cy="imdbRating"
                type="text"
              />
              <ValidatedField
                label={translate('movieCatalogServiceApp.movieCatalogMovie.imdbVotes')}
                id="movie-imdbVotes"
                name="imdbVotes"
                data-cy="imdbVotes"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/movie-catalog/movie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MovieUpdate;
