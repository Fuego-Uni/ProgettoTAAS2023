<script lang="ts">
  import { getAllFilms } from "$lib/film";
  import type { FilmData } from "$lib/types";
  import { onMount } from "svelte";
  import { fade, fly } from 'svelte/transition';
  let current_elements: FilmData[];
  let page: number = Math.floor(Math.random() * 100) + 1;
  let slice: number[] = [0, 2];
  onMount(async () => {
    current_elements = await getAllFilms(1);
    // get only the first 2 elements
    current_elements = current_elements.slice(0, 2);
    //console.log(current_elements);
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
{#key slice}
<div class="small_carosel_preview" in:fly={{ duration: 500 }}>
  {#await getAllFilms(page) then films1}
    {#each films1.slice(slice[0], slice[1]) as film}
      <div class="small_preview">
        <img src={film.poster_path} alt="" />
        <div class="info">
          <h3>{film.title}</h3>
          <p>{film.release_date}</p>
        </div>
      </div>
    {/each}
  {:catch error}
    <p>Error: {error.message}</p>
  {/await}
  <!-- svelte-ignore a11y-click-events-have-key-events -->
  <div
    class="next_page"
    on:click={() => {
      slice[0] = slice[0] + 2;
      slice[1] = slice[1] + 2;
      if (slice[1] > 20) {
        slice[0] = 0;
        slice[1] = 2;
        page = page + 1;
      }
    }}
  >
    <svg
      version="1.1"
      id="icons_1_"
      xmlns="http://www.w3.org/2000/svg"
      x="0"
      y="0"
      viewBox="0 0 128 128"
      style="enable-background:new 0 0 128 128"
      xml:space="preserve"
      ><style>
        .st0 {
          /* display: none; */
        }
        .st1 {
          /* display: inline; */
        }
        .st2 {
          fill: #ffff;
        }
      </style><g id="row1_1_"
        ><g id="_x31__3_"
          ><path
            class="st2"
            d="M64 0C28.7 0 0 28.7 0 64s28.7 64 64 64 64-28.7 64-64S99.3 0 64 0zm0 121.6C32.2 121.6 6.4 95.8 6.4 64S32.2 6.4 64 6.4s57.6 25.8 57.6 57.6-25.8 57.6-57.6 57.6zM49.2 38.4 73.6 64 49.2 89.6h13.5L86.4 64 62.7 38.4H49.2z"
            id="_x32__2_"
          /></g
        ></g
      ></svg
    >
  </div>
</div>
{/key}

<style lang="scss">
  img {
    width: 100%;
    height: 20rem;
    object-fit: cover;
    border-radius: 6px;
  }
  .small_carosel_preview {
    color: var(--color-ui-text-unfocused);
    display: flex;
    flex-direction: row;
    gap: 1rem;
    padding-top: 1rem;
    min-height: fit-content;
  }
  .next_page{
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    svg{
        align-self: center;
        width: 50px;
        height: 50px;
        object-fit: cover;
    }
  }
  .small_preview {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    width: 100%;
    height: 100%;
  }
</style>
