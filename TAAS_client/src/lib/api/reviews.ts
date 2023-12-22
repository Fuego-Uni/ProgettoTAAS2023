import axios from "axios"
import type { MediaData, Review } from "../types"
import { getUserInfo } from "$lib/store/user_info_store"
import { initiateAxios } from "$lib/authentication"

export async function postReview(mediaId: number, vote: number, note: string) {
  initiateAxios()

  axios.post(
    'http://localhost:8080/review/add', {}, { params: { mediaId, vote, note } }
  )
}

export async function getReviews(mediaId: number): Promise<Review[]> {
  initiateAxios()
  
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

export async function getFriendReviews(mediaId: number): Promise<Review[]> {
  let reviews: Review[] = await getReviews(mediaId)

  let currentUser = (await getUserInfo())!.email

  return reviews.filter((review: Review) => review.user !== currentUser)
}

export async function getReviewedMedia(): Promise<number[]> {
  initiateAxios()

  return axios.get(
    'http://localhost:8080/review/media', {}
  )
    .then((response) => {
      return response.data.map((media: MediaData) => media.id)
      // console.log(response.data)
    }).catch((err) => {
      // console.error("Unable to get reviewed films")
      console.error(err)
    })
}