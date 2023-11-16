<script lang="ts">
  import type { ContentPreviewData, MediaData } from "$lib/types";
  import { tick, onMount } from "svelte";
  import ContentPreview from "./ContentPreview.svelte";
  import { createEventDispatcher } from "svelte";
  import { goto } from "$app/navigation";

  const dispatch = createEventDispatcher();
  const slots = 5

  export let items: MediaData[];
  export let title: string;

  let offset = 0;

  onMount(async () => {
    // listElement.addEventListener("scroll", calculateActiveItem);
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-carousel">
  <div class="elements" style={`transform: translateX(-${offset*16}rem)`}>
    {#each items as item, i}
      <ContentPreview
        active={i == offset}
        data={item}
        on:click={() => {}}
      />
    {/each}
  </div>

  <div class="arrow left" on:click={() => {
    offset = Math.max(offset - 1, 0)
    // console.log(offset)
  }} />
  <div class="arrow right" on:click={() => {
    offset = Math.min(offset + 1, items.length - slots)
    // console.log(offset)
  }} />
</div>

<style lang="scss">
  .content-carousel {
    --arrow-width: 1rem;

    display: grid;
    grid-template-columns: var(--arrow-width) calc(25rem) var(--arrow-width) 1fr;
    grid-template-rows: 100%;

    overflow: hidden;
    height: 100%;

    .arrow {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      width: 100%;

      background-color: red;

      z-index: 1;
    }

    .elements {
      grid-column: 1 / 5;
      grid-row: 1;
      height: 100%;
      width: fit-content;

      display: flex;
      overflow: hidden;

      padding: 0 var(--arrow-width) 0 var(--arrow-width);
      gap: 1rem;

      transition: all 0.3s ease-in-out;
    }

    .arrow.left {
      grid-column: 1;
      grid-row: 1;
    }

    .arrow.right {
      grid-column: 3;
      grid-row: 1;
    }
  }

// MOBILE
@media (max-width: 950px) {
}
</style>
