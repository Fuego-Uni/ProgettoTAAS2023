<script lang="ts">
  import type { ContentPreviewData, FilmData } from "$lib/types";
  import { tick, onMount } from "svelte";
  import ContentPreview from "./ContentPreview.svelte";
  import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();

  export let items: FilmData[];
  export let title: string;

  let active = 0;
  let listElement: any;

  async function calculateActiveItem() {
    await tick(); // Wait for the next microtask
    const children = Array.from(listElement.children);
    const midpoint = listElement.scrollLeft + listElement.clientWidth / 2;
    let index = 0;
    let itemStartX = 0;
    if (listElement.scrollLeft === 0) {
      active = 0;
      return;
    }

    for (let i = 0; i < children.length; i++) {
      const itemWidth = children[i].offsetWidth;
      if (itemStartX + itemWidth > midpoint) {
        index = i;
        break;
      }
      itemStartX += itemWidth;
    }

    active = index;
  }
  onMount(async () => {
    listElement.addEventListener("scroll", calculateActiveItem);
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="content-carousel">
  <div class="botton-wrapper">  
    <div class="button first" on:click={() => dispatch("newpage")}>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25"
        ><path
          style="fill:#FFFFFF"
          d="m17.5 5.999-.707.707 5.293 5.293H1v1h21.086l-5.294 5.295.707.707L24 12.499l-6.5-6.5z"
          data-name="Right"
        /></svg
      >
    </div>
    <div class="button " on:click={() => dispatch("newpage")}>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25"
        ><path
          style="fill:#FFFFFF"
          d="m17.5 5.999-.707.707 5.293 5.293H1v1h21.086l-5.294 5.295.707.707L24 12.499l-6.5-6.5z"
          data-name="Right"
        /></svg
      >
    </div>
  </div>
  <div class="title">{title}</div>
  <div class="list" bind:this={listElement}>
    {#each items as item, i}
      <ContentPreview
        data={item}
        active={i == active}
        on:click={() => {
          active = i;
        }}
      />
    {/each} 
  </div>
</div>

<style lang="scss">
  .botton-wrapper{
    position: fixed;
    display: flex;
    justify-content: space-between;
    width: calc(100% - 7rem); 
    z-index: 0;
    pointer-events: none;
  }
  .first {
    svg {
      transform: rotate(180deg);
    }
  }
  .button {
    display: flex;
    align-items: center;
    justify-content: center;
    min-width: 50px;
    height: 600px;
    /*  border: solid red 3px; */
    box-sizing: border-box;
    cursor: pointer;
    svg {
      width: 50px;
      height: 100px;
      object-fit: cover;
    }
  }
  .content-carousel {
    display: flex;
    flex-direction: column;

    gap: 0.5rem;
    color: var(--color-ui-text-focused);
    font-size: 1.5rem;

    .title {
      text-shadow: rgba(255, 255, 255, 0.6) 0px 0px 20%;
    }

    .list {
      display: flex;
      gap: 1rem;
      scroll-snap-type: x mandatory;
      overflow-x: scroll;
      min-height: 600px;
    }
  }

  // MOBILE
  @media (max-width: 950px) {
    .content-carousel {
      .list {
        flex-direction: column;
      }
    }
  }
</style>
