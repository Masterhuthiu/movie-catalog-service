import dayjs from 'dayjs';

export interface IMovie {
  id?: string;
  title?: string;
  plot?: string | null;
  fullplot?: string | null;
  year?: number | null;
  runtime?: number | null;
  rated?: string | null;
  released?: dayjs.Dayjs | null;
  genres?: string | null;
  cast?: string | null;
  languages?: string | null;
  poster?: string | null;
  imdbRating?: number | null;
  imdbVotes?: number | null;
}

export const defaultValue: Readonly<IMovie> = {};
