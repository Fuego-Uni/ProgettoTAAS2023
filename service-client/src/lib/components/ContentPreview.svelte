<script lang="ts">
  import { createEventDispatcher } from "svelte";
  import Rating from "./Rating.svelte";
  import type { MediaData } from "$lib/types";

  const dispatch = createEventDispatcher();

  export let data: MediaData;
  export let active = false;
  export let rating_type: "friend" | "public" = "friend";
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-preview" class:active on:click={() => {dispatch("click")}} >
  <img src={data.backdrop_path} alt="" class="banner" />
  <div class="infobox-wrap">
    <div class="infobox">
      <div class="section title">{data.title}</div>
      <div class="section date">{data.release_date}</div>
      <div class="section rating">
        <Rating rating_average={data.rating_average} />
      </div>
    </div>
  </div>
</div>

<style lang="scss">
  .content-preview {
    width: 15rem;
    height: 100%;
    height: 10rem;
    display: grid;
    grid-template-rows: 1fr 6rem;
    grid-template-columns: 1fr 1fr;

    transition: all 0.3s ease-in-out;
    cursor: pointer;
    border-radius: 0.3rem;
    overflow: hidden;

    margin: 0 1rem;

    &.active {
      // aspect-ratio: 5/2;
      height: 10rem;
      width: 25rem;
      border-radius: 0;
      margin: 0 1.5rem;
    }

    .banner {
      grid-row: 1 / 3;
      grid-column: 1 / 3;

      width: 100%;
      height: 100%;

      object-fit: cover;
    }

    .infobox-wrap {
      display: none;
      opacity: 0;
    }

    &.active > .infobox-wrap {
      opacity: 100%;
      display: flex;
      height: 100%;
      width: 100%;

      grid-row: 2 / 3;
      grid-column: 1 / 3;

      padding: 0;

      .infobox {
        min-width: 10rem;
        display: grid;
        height: 100%;
        width: 100%;

        padding: 0.5rem;

        color: white;

        background: rgba(35, 34, 40, 0.8);
        box-shadow: 10px 10px 10px 0px rgba(0, 0, 0, 0.25);
        backdrop-filter: blur(2px);

        display: flex;
        gap: 0.5rem;

        .section {
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .title {
          grid-area: title;
          font-size: 1rem;
          justify-content: flex-start;
          font-weight: bold;
        }

        .date {
          grid-area: date;
          font-size: 0.8rem;
          justify-content: flex-start;
          flex: 1;
        }
      }
    }
  }

  // MOBILE
  @media (max-width: 950px) {
    .content-preview {
      aspect-ratio: unset;
      // height: 13rem;

      &.active {
        width: var(--preview-width);
        aspect-ratio: unset;
      }
    }
  }
</style>
