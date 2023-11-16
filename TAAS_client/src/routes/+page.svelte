<script lang="ts">
  import ContentPreview from "$lib/components/ContentPreview.svelte";
  import TestBanner from "$lib/assets/testimg.png";
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { filmStore } from "$lib/store/store";
  import { onMount } from "svelte";
  import { get } from "svelte/store";
  import { getAllFilms, getFilmInfo } from "$lib/film";

  let large_active = 0;
  let small_active = 0;

  let page = 1;
  //let films: FilmData[] = [];
  $: {
    /*  films = get(filmStore);
    console.log(films) */
  }
  onMount(async () => {
    //if(get(filmStore).length == 0) filmStore.set(await getAllFilms(1));
  });
</script>

<div class="page">
  <!-- <div class="carousel-wrapper">   -->
  {#await getAllFilms(page) then films1}
    <ContentCarousel
      title="LISTA 1"
      items={films1}
      on:newpage={() => (page = page + 1)}
    />
  {/await}
  <!-- </div> -->
  <!-- <div class="carousel-wrapper"> -->
  {#await getAllFilms(2) then films1}
    <ContentCarousel title="LISTA 1" items={films1} />
  {/await}
  <!-- </div> -->
  <!-- <div class="carousel-wrapper"> -->
  {#await getAllFilms(3) then films1}
    <ContentCarousel title="LISTA 1" items={films1} />
  {/await}
  <!-- </div> -->
</div>

<style lang="scss">
  .page {
    --gap: 1.5rem;

    height: 100%;
    width: 100%;

    display: flex;
    flex-direction: column;

    padding: 1rem 1rem 1rem 1rem;
    gap: var(--gap);
    overflow-x: hidden;
  }

  // .carousel-wrapper {
  //   height: 100%;
  //   width: 100%;
  //   display: flex;
  //   flex-direction: column;
  //   gap: var(--gap);
  // }

  // MOBILE
  @media (max-width: 950px) {
    .page {
      padding: 1rem;
      padding-top: 0;
      margin-top: 1rem;
      overflow-y: scroll;
    }
  }
</style>
