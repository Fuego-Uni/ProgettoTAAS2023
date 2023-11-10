<script lang="ts">
  import type { PageData } from "./$types";
  import { getAllFilms, getFilmInfo, reviewFilm } from "$lib/film";
  import { onMount } from "svelte";
  import type { FilmData } from "$lib/types";
  import ContentPreview from "$lib/components/ContentPreview.svelte";
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import SmallPreview from "$lib/components/SmallPreview.svelte";
  export let data: PageData;
  console.log(data.film_id);
  let page = 1;
  let film_id = data.film_id.toString();
  function handleReview() {
    reviewFilm(Number(data.film_id), "testtesttest", "notenotenotenote");
  }
  let film_info: FilmData;
  onMount(async () => {
    film_info = await getFilmInfo(Number(film_id));
    console.log(film_info);
  });
</script>

<div class="film_container">
  <!-- <button on:click={handleReview}> test</button> -->
  <div class="left">
    <div class="top">
      <img
        src={film_info?.backdrop_path}
        alt=""
        style="width: 100%; height: 100%; object-fit: cover; border-radius: 6px"
      />
    </div>
    <div class="bottom">
      <SmallPreview />
    </div>
  </div>
  <div class="right">
    <div class="info_container">
      <div class="info">
        <h1 style="font-size: 50px">{film_info?.title}</h1>
        <p>{film_info?.release_date}</p>
        <p
          style="
        overflow: hidden;
        text-overflow: ellipsis;"
        >
          {film_info?.overview}
        </p>
      </div>
      <div class="evaluete" style="">
        <!--  <h1>Nota</h1>
        <h1>Nota</h1>
        <h1>Nota</h1> -->
      </div>
    </div>

    <div class="review_container" />
  </div>
</div>

<style lang="scss">
  .film_container {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    height: 100vh;
    padding: 6px;
  }
  .left {
    flex: 1.1;
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    .top {
      padding: 6px;
      flex: 1;
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .bottom {
      flex: 1;
      height: fit-content;
    }
  }
  .right {
    color: var(--color-ui-text-unfocused);
    flex: 1;
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  .info_container {
    flex: 1;
    display: flex;
    gap: 6px;
    justify-content: center;
    align-items: center;
    .info {
      flex: 1.4;
      height: 100%;
      display: flex;
      flex-direction: column;
      /* justify-content: space-between; */
    }
    .evaluete {
      flex: 1;
      
      padding: 10px;
      border-radius: 10px;
      height: 80%;
      width: 80%;
      background-color: rgba(217, 217, 217, 0.15);
    }
  }
  .review_container {
    flex: 1;
  }
</style>
