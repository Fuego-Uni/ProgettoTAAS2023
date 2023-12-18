import axios from "axios"
import type { MediaData, Review } from "../types"

const apiKey = '1fdc2c6e1ad61d4f607f73e1f7e179a9'

export const getFilmInfo = async (id: number): Promise<MediaData> => {
  const response = await fetch(`https://api.themoviedb.org/3/movie/${id}?api_key=${apiKey}&language=it-US`)
  const data = await response.json()
  const photo = { poster_path: data.poster_path, backdrop_path: data.backdrop_path }

  const imageUtr = `https://image.tmdb.org/t/p/w500${photo.poster_path}`
  const imageUtr2 = `https://image.tmdb.org/t/p/w500${photo.backdrop_path}`

  return {
    id: data.id,
    title: data.title,
    overview: data.overview,
    release_date: data.release_date,
    poster_path: imageUtr,
    backdrop_path: imageUtr2,
    rating_average: data.vote_average,
    vote_count: data.vote_count,
  }
}

export async function getFilmList(page: number, list: 'upcoming' | 'top_rated' | 'popular' = 'upcoming') {
  if (list !== 'upcoming' && list !== 'top_rated' && list !== 'popular') throw new Error('Invalid list: ' + list)

  const response = await fetch(`https://api.themoviedb.org/3/movie/${list}?api_key=${apiKey}&language=it-US&page=${page}`)
  
  const data = await response.json()

  return data.results.map((film: any) => ({
    id: film.id,
    title: film.title,
    overview: film.overview,
    release_date: film.release_date,
    poster_path: `https://image.tmdb.org/t/p/w500${film.poster_path}`,
    backdrop_path: `https://image.tmdb.org/t/p/w500${film.backdrop_path}`,
    rating_average: film.vote_average,
    vote_count: film.vote_count,
  }))
}


// ====================================================================================================
// ===== TV SHOWS =====================================================================================
// ====================================================================================================

export const getShowInfo = async (id: number): Promise<MediaData> => {
  const response = await fetch(`https://api.themoviedb.org/3/tv/${id}?api_key=${apiKey}&language=it-US`)
  const data = await response.json()
  console.log(data)
  const photo = { poster_path: data.poster_path, backdrop_path: data.backdrop_path }

  const imageUtr = `https://image.tmdb.org/t/p/w500${photo.poster_path}`
  const imageUtr2 = `https://image.tmdb.org/t/p/w500${photo.backdrop_path}`

  return {
    id: data.id,
    title: data.name,
    overview: data.overview,
    release_date: data.first_air_date,
    poster_path: imageUtr,
    backdrop_path: imageUtr2,
    rating_average: data.vote_average,
    vote_count: data.vote_count,
  }
}

export async function getShowList(page: number, list: 'airing_today' | 'top_rated' | 'popular' = 'airing_today') {
  if (list !== 'airing_today' && list !== 'top_rated' && list !== 'popular') throw new Error('Invalid list: ' + list)

  const response = await fetch(`https://api.themoviedb.org/3/tv/${list}?api_key=${apiKey}&language=it-US&page=${page}`)
  
  const data = await response.json()

  return data.results.map((show: any) => ({
    id: show.id,
    title: show.name,
    overview: show.overview,
    release_date: show.first_air_date,
    poster_path: `https://image.tmdb.org/t/p/w500${show.poster_path}`,
    backdrop_path: `https://image.tmdb.org/t/p/w500${show.backdrop_path}`,
    rating_average: show.vote_average,
    vote_count: show.vote_count,
  }))
}