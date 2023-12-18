<script lang="ts">
  import type { ContentPreviewData, MediaData } from "$lib/types";
  import { tick, onMount } from "svelte";
  import ContentPreview from "./ContentPreview.svelte";
  import { createEventDispatcher } from "svelte";
  import { goto } from "$app/navigation";

  export let items: MediaData[];
  export let title: string;
  export let type: "film" | "show";

  let offset = 0;
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-carousel">
  <div class="title">{title}</div>

  <div class="elements" style={`transform: translateX(-${offset*17+1}rem)`}>
    {#each items as item, i}
      <ContentPreview
        active={i == offset}
        data={item}
        on:click={() => {
          console.log("click")
          goto(`${type}/${item.id}`)
        }}
      />
    {/each}
  </div>

  <div class="arrow left ui-interactive" on:click={() => {
    offset = Math.max(offset - 1, 0)
  }} >
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l192 192c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L77.3 256 246.6 86.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-192 192z"/></svg>
  </div>
  <div class="arrow right ui-interactive" on:click={() => {
    offset = Math.min(offset + 1, items.length - 1)
  }} >
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M310.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-192 192c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L242.7 256 73.4 86.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l192 192z"/></svg>
  </div>
</div>

<style lang="scss">
  .content-carousel {
    display: grid;
    grid-template-columns: 1.5rem 25rem 1.5rem 1fr;
    grid-template-rows: 1rem 1fr;
    flex-wrap: wrap;
    overflow: hidden;
    min-height: 15rem;

    row-gap: 1rem;

    .title {
      grid-row: 1;
      grid-column: 1 / 5;
      color: white;
    }

    .elements {
      grid-column: 1 / 5;
      grid-row: 2;
      height: 100%;
      width: fit-content;

      display: flex;
      overflow: hidden;

      padding: 0 1rem;
      // gap: 2rem;

      transition: all 0.3s ease-in-out;
    }

    .arrow {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      width: 100%;
      padding: 0;
      z-index: 1;
      border-radius: 0;
    }

    .arrow.left {
      grid-column: 1;
      grid-row: 2;
      border-top-right-radius: 0;
      border-bottom-right-radius: 0;
    }

    .arrow.right {
      grid-column: 3;
      grid-row: 2;
      border-top-left-radius: 0;
      border-bottom-left-radius: 0;
    }
  }

// MOBILE
@media (max-width: 950px) {
}
</style>
