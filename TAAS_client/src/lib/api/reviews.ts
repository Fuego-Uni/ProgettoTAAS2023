import axios from "axios"
import type { MediaData, Review } from "../types"

export async function postReview(mediaId: number, vote: number, note: string) {
  axios.post(
    'http://localhost:8080/review/add', {}, { params: { mediaId, vote, note } }
  )
}

export async function getReviews(mediaId: number): Promise<Review[]> {
  return axios.get(
    'http://localhost:8080/review/friends', { params: { mediaId }, }
  )
    .then((response) => {
      return response.data.map((review: Review) => ({
        user: review.user,
        vote: review.vote,
        note: review.note
      }))
    }).catch((err) => {
      console.error("Unable to get reviews")
    })
}

export async function getReviewedFilms(): Promise<MediaData[]> {
  // TODO: implement this function

  return []
}

export async function getReviewedShows(): Promise<MediaData[]> {
  // TODO: implement this function

  return []
}