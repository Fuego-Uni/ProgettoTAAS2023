<script lang="ts">
  import { getFilmInfo, getFilmList } from "$lib/api/moviedb_api";
  import type { MediaData, Review } from "$lib/types";
  import { onMount } from "svelte";
  import type { PageData } from "./$types";
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { getReviews, postReview } from "$lib/api/reviews";
  import Rating from "$lib/components/Rating.svelte";
  import { getUserInfo } from "$lib/store/user_info_store";
  import FriendReview from "$lib/components/FriendReview.svelte";
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import toast, { Toaster } from 'svelte-french-toast';

  export let data: PageData;
  let film_id: number = Number.parseInt(data.film_id);
  let media_info: MediaData;
  let film_review: Review[]= [];

  let review_vote_input: number = 0;
  let review_note_input: string = "";

  $: { review_vote_input = review_vote_input > 10 ? 10 : review_vote_input; }

  async function pubblicaReview() {
    if(review_vote_input == 0 || review_note_input == "") { return; }

    if(film_id == null) {
      return
    }

    console.log(film_id)

    postReview(film_id, review_vote_input, review_note_input);

    review_vote_input = 0;
    review_note_input = "";
  }

  async function updateReviews() {
    media_info = await getFilmInfo(film_id);
    let reviews = await getReviews(film_id);
    let user = await getUserInfo();

    let my_review = reviews.find(review => review.user == user!.email);
    if(my_review) {
      review_vote_input = my_review.vote;
      review_note_input = my_review.note;
    }

    film_review = reviews.filter(review => review.user != user!.email);
  }

  onMount(async () => {
    updateReviews();

    mainSocketSetHandler("review-added", updateReviews)

    mainSocketSetHandler("review-updated", updateReviews)
  }); 
</script>

<div class="page">
  <div class="left-wrap">
    <div class="poster-wrap">
      <img src={media_info?.backdrop_path} alt="" />
      <div class="info-container">
        <h1>{media_info?.title}</h1>
        <p>{media_info?.release_date}</p>
        <p>{media_info?.overview}</p>
      </div>
    </div>
    <div class="carousel-wrap">
      {#await getFilmList(1, 'popular') then films}
        <ContentCarousel title="" items={films} type="film" />
      {/await}
    </div>
  </div>
  <div class="right-wrap">
    <div class="review-post">
      <input class="vote-input ui-interactive" type="number" name="" id="" min="0" max="10" bind:value={review_vote_input}>
      <textarea class="note-input ui-interactive" name="" id="" cols="30" rows="10" bind:value={review_note_input}></textarea>
      <input class="publish-button ui-interactive" type="button" value="pubblica recensione" on:click={pubblicaReview}>
    </div>

    <div class="review-list">
      {#each film_review as review}
        <FriendReview review={review} />
      {/each}
    </div>
  </div>
</div>

<style lang="scss">
  .page {
    display: flex;
    height: 100%;
    width: 100%;
    padding: 1rem;

    gap: 2rem;

    .left-wrap {
      height: 100%;
      width: 60%;
    }

    .right-wrap {
      height: 100%;
      width: 40%;
    }

    .poster-wrap {
      height: 60%;
      width: 100%;
      display: grid;
      grid-template-columns: 100%;
      grid-template-rows: 100%;

      justify-content: center;
      align-items: center;

      .info-container {
        grid-row: 1;
        grid-column: 1;

        height: auto;
        width: 100%;

        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        align-self: flex-end;
        padding: 1rem;

        color: white;

        background: rgba(35, 34, 40, 0.8);
        box-shadow: 10px 10px 10px 0px rgba(0, 0, 0, 0.25);
        backdrop-filter: blur(2px);
      }

      img {
        grid-row: 1;
        grid-column: 1;

        height: 100%;
        width: 100%;
        object-fit: cover;
      }
    }

    .carousel-wrap {
      height: 40%;
      width: 100%;
    }

    .review-post {
      height: 50%;
      width: 100%;

      display: flex;
      flex-direction: column;

      gap: 0.2rem;

      input, textarea {
        width: 100%;
        border: none;
        resize: none;
      }

      input:focus, textarea:focus {
        outline: none;
      }

      .vote-input {
        height: 2.5rem;
      }

      .note-input {
      }

      .publish-button {
        height: 2.5rem;
      }
    }

    .review-list {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;

      overflow-y: scroll;
    }
  }
</style>