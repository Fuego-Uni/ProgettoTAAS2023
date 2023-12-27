import axios from "axios"
import type { MediaData, Review } from "../types"
import { getUserInfo } from "$lib/store/user_info_store"
import { initiateAxios } from "$lib/authentication"

export async function postReview(mediaId: number, vote: number, note: string, type: "film" | "show") {
  initiateAxios()

  axios.post(
    'http://localhost:8080/review/add', {}, { params: { mediaId, vote, note, type } }
  )
}

export async function getReviews(mediaId: number, type: "film" | "show"): Promise<Review[]> {
  initiateAxios()
  
  return axios.get(
    'http://localhost:8080/review/friends', { params: { mediaId, type }, }
  )
    .then((response) => {
      let out = response.data.map((review: Review) => ({
        user: review.user,
        vote: review.vote,
        note: review.note
      }))

      console.log(out)

      return out
    }).catch((err) => {
      console.error("Unable to get reviews")
    })
}

export async function getFriendReviews(mediaId: number, type: "film" | "show"): Promise<Review[]> {
  let reviews: Review[] = await getReviews(mediaId, type)

  let currentUser = (await getUserInfo())!.email

  return reviews.filter((review: Review) => review.user !== currentUser)
}

export async function getReviewedMedia(type: "film" | "show"): Promise<number[]> {
  initiateAxios()

  return axios.get(
    'http://localhost:8080/review/media', {
      params: { type }
    }
  )
    .then((response) => {
      return response.data.map((media: MediaData) => media.id)
      // console.log(response.data)
    }).catch((err) => {
      // console.error("Unable to get reviewed films")
      console.error(err)
    })
}