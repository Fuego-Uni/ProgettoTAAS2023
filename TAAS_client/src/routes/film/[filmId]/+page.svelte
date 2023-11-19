<script lang="ts">
  import { getAllFilmReview, getFilmInfo, getAllFilms } from "$lib/film";
  import type { MediaData, Review } from "$lib/types";
  import { onMount } from "svelte";
  import type { PageData } from "./$types";
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";

  export let data: PageData;

  let film_id = data.film_id.toString();
  let media_info: MediaData;
  let film_review: Review[]= [];

  onMount(async () => {
    media_info = await getFilmInfo(film_id);
    film_review = await getAllFilmReview(film_id)
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
      {#await getAllFilms(2) then films1}
        <ContentCarousel title="" items={films1} />
      {/await}
    </div>
  </div>
  <div class="right-wrap">

  </div>
</div>

<style lang="scss">
  .page {
    display: flex;
    height: 100%;
    width: 100%;
    padding: 1rem;

    .left-wrap {
      height: 100%;
      width: 50%;
    }

    .right-wrap {
      height: 100%;
      width: 50%;
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
  }
</style>