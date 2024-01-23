<script lang="ts">
  import type { ContentPreviewData, MediaData } from "$lib/types";
  import { tick, onMount } from "svelte";
  import ContentPreview from "./ContentPreview.svelte";
  import { createEventDispatcher } from "svelte";
  import { goto } from "$app/navigation";

  export let items: MediaData[];
  export let title: string;
  export let type: "film" | "show";

  let element_component: HTMLElement;

  let offset = 0;

  function calculateOffset(_: any = undefined) {
    if(!element_component) return;

    // set as css variable
    element_component.style.setProperty("--offset", offset.toString())
  }

  $: {
    calculateOffset(offset);
  }

  onMount(() => {
    calculateOffset();
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-carousel">
  <div class="title">{title}</div>

  <div class="elements" bind:this={element_component}>
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
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512"><path d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l192 192c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L77.3 256 246.6 86.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-192 192z"/></svg>
  </div>
  <div class="arrow right ui-interactive" on:click={() => {
    offset = Math.min(offset + 1, items.length - 1)
  }} >
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512"><path d="M310.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-192 192c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L242.7 256 73.4 86.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l192 192z"/></svg>
  </div>
</div>

<style lang="scss">
  .content-carousel {
    --arrow-width: 1.5rem;
    --preview-width: 25rem;
    --gap: 2rem;

    row-gap: 1rem;

    width: calc(var(--arrow-width) + var(--preview-width) + var(--arrow-width));

    display: grid;

    grid-template-columns: var(--arrow-width) var(--preview-width) var(--arrow-width);
    grid-template-rows: 1rem 1fr;

    .title {
      grid-row: 1;
      grid-column: 1 / 5;
      color: white;
    }

    .elements {
      grid-row: 2;
      grid-column: 2;
      height: 100%;
      width: fit-content;

      display: flex;
      flex-direction: row;

      --offset-amount: calc(
        var(--arrow-width) * -1 + 15rem * var(--offset) * -1 + var(--gap) * var(--offset) * -1
      );

      transform: translateX(var(--offset-amount));

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
  .content-carousel {
    --arrow-width: 1.5rem;
    --preview-width: calc(100vw - var(--arrow-width) * 2 - 2rem);

    width: 100%;
    height: 15rem;

    display: grid;

    grid-template-columns: var(--arrow-width) var(--preview-width) var(--arrow-width) 1fr;
    grid-template-rows: 1rem 1fr;

    .title {
      color: white;
    }

    .elements {

      grid-column: 2;
      // width: 100%;
      width: fit-content;

      // transform: translateX(0);
    }
  }
}
</style>
