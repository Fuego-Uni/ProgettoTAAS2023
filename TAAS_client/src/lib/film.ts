import type { FilmData } from "./types"

const apiKey = '1fdc2c6e1ad61d4f607f73e1f7e179a9'



export const getFilmInfo = async (id: number): Promise<FilmData> => {
  const response = await fetch(`https://api.themoviedb.org/3/movie/${id}?api_key=${apiKey}&language=it-US`)
  const data = await response.json()
  const photo = {poster_path: data.poster_path, backdrop_path: data.backdrop_path}

  const imageUtr = `https://image.tmdb.org/t/p/w500${photo.poster_path}`
  const imageUtr2 = `https://image.tmdb.org/t/p/w500${photo.backdrop_path}`
  
  return{
    id: data.id,
    title: data.title,
    overview: data.overview,
    release_date: data.release_date,
    poster_path: imageUtr,
    backdrop_path: imageUtr2,
    vote_average: data.vote_average,
    vote_count: data.vote_count,
  }
}

export const getAllFilms = async (page: number): Promise<FilmData[]> => {
  const response = await fetch(`https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=it-US&page=${page}`)
  const data = await response.json()
  return data.results.map((film: any) => ({
    id: film.id,
    title: film.title,
    overview: film.overview,
    release_date: film.release_date,
    poster_path: `https://image.tmdb.org/t/p/w500${film.poster_path}`,
    backdrop_path: `https://image.tmdb.org/t/p/w500${film.backdrop_path}`,
    vote_average: film.vote_average,
    vote_count: film.vote_count,
  })) 
}