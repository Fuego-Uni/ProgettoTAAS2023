<script lang="ts">
	import { createEventDispatcher } from 'svelte';
  import Rating from './Rating.svelte';
  import type { ContentPreviewData } from '$lib/types';

	const dispatch = createEventDispatcher();

  export let data: ContentPreviewData;
  export let active = false;
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-preview"
  class:active
  on:click={() => dispatch('click')}
>
  <img src={data.banner} alt="" class="banner">
  <div class="infobox-wrap">
    <div class="infobox">
      <div class="section title">{data.title}</div>
      <div class="section date">({data.date})</div>
      <div class="section rating">
        <Rating rating_average={4} rating_personal={3} />
      </div>
    </div>
  </div>
</div>

<style lang="scss">
  .content-preview {
    height: 100%;

    display: grid;
    grid-template-rows: 1fr 3rem;
    grid-template-columns: 1fr 1fr;
    &:not(.large) { grid-template-columns: 2fr 1fr; }

    border-radius: 0.3rem;
    overflow: hidden;
    
    box-shadow: 10px 10px 10px 0px rgba(0, 0, 0, 0.25); 

    transition: all 0.3s ease-in-out;

    cursor: pointer;

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
  }

  .content-preview.active {
    .infobox-wrap {
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

        border-radius: 0 0 0.3rem 0.3rem;

        padding: 0.5rem;

        color: white;

        background: rgba(35, 34, 40, 0.80);
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
  
  .content-preview.active {
    aspect-ratio: 5/2;
  }
  .content-preview:not(.active) {
    aspect-ratio: 3/2;
  }

// MOBILE
@media (max-width: 950px) {
  .content-preview {
    width: 100%;
    height: 13rem;
    aspect-ratio: unset;
  }
}
</style>