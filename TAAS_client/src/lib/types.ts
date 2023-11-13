export type ContentPreviewData = {
  banner: string,
  title: string,
  date: string,
  rating_average: number,
  rating_personal: number,
}

export type UserInfo = {
  name: string,
  email: string,
  role: string,
}
export type FilmData = {
  id: number,
  title: string,
  overview: string,
  release_date: string,
  poster_path: string,
  backdrop_path: string,
  vote_average: number,
  vote_count: number,
}

export type Review = {
  vote: number;
  note: string;
  user: any;
}