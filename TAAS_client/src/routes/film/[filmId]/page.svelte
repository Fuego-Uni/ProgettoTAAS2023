<script lang="ts">
  import type { PageData } from "./$types";
  import { getAllFilms, getFilmInfo, reviewFilm, getAllFilmReview } from "$lib/film";
  import { onMount } from "svelte";
  import type { FilmData, Review } from "$lib/types";
  import ContentPreview from "$lib/components/ContentPreview.svelte";
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import SmallPreview from "$lib/components/SmallPreview.svelte";
  import Rating from "$lib/components/Rating.svelte";
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  export let data: PageData;
  
  let page = 1; 
  let film_id = data.film_id.toString();
  let vote_average: number;
  let vote_note = "";

// !TODO: restore della tua recensione se ce
  
  function handleReview() {
    reviewFilm(Number(data.film_id), vote_average.toString(), vote_note);
  } 
  let film_info: FilmData;
  let film_review: Review[]= [];
  onMount(async () => {
    film_info = await getFilmInfo(Number(film_id));
    film_review =await getAllFilmReview(Number(film_id))

    mainSocketSetHandler("notification",async (data) => {
      console.log("film_handler", data );
      // refresh film review
      film_review = await getAllFilmReview(Number(film_id))
      // TODO: show notification  
    }, "film" )
  }); 
  $: console.log(film_review, "film_review id", film_id)
</script>

<div class="film_container">
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
        text-overflow: ellipsis;
        <!-- max-height: 70px; -->
        "
        >
          {film_info?.overview}
        </p>
      </div>
      <div class="evaluete" style="">
        <Rating rating_personal={0} rating_average={vote_average} on:clic_rating={(params)=> {
          console.log("test",params.detail )
          vote_average = params.detail;
          }} />
        <!-- <input type="text" class="input"> -->
        <textarea id="message" class="input" name="message" rows="4" cols="50" bind:value={vote_note}></textarea>
        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div class="button" on:click={handleReview}>
          Pubblica
        </div>
      </div>
    </div>
    Recensioni
    <div class="review_container">

        
        
        {#each film_review as rev }
          <div class="review">
            <div class="review_top">
              {rev?.user}
              {rev?.vote}
            </div>
            {rev?.note}
          </div>
        {/each}
      
        

   
    </div>
  </div>
</div>

<style lang="scss">
  .film_container {
    display: flex;
    flex-direction: row;
    
    justify-content: center;
    max-height: 100%;
    min-height: 100%;
    padding: 6px;
    
  }
  .left {
    flex: 1.1;
    min-height: 100%;
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
    max-height: 100%;
    max-width: 50%;
    display: flex;
    flex-direction: column;
    gap: 6px;
  }
  .info_container {
    flex: 1;
    display: flex;
    gap: 6px;
    justify-content: center;
    align-items: center;
    .info {
      flex: 1.4;
      max-height: 100%;
      display: flex;
      flex-direction: column;
      /* justify-content: space-between; */
    }
    .evaluete {
      flex: 1;
      padding: 10px;
      gap: 10px;
      border-radius: 10px;
      height: 100%;
      width: 80%;
      background-color: rgba(217, 217, 217, 0.15);
      display: flex;
      flex-direction: column;
      justify-content: start;

      .input{
        height: 100%;
        width: 100%;
        border-radius: 10px;
        border: none;
        background-color: rgba(217, 217, 217, 0.15);
        color: var(--color-ui-text-unfocused);
        padding: 10px;
        /* font-size: 20px; */
        outline: none;
        text-align: start;
      }
      .button{
        height: 50px;
        width: 100%;
        border-radius: 10px;
        border: none;
        background-color: var(--color-main-accent);
        color: white;
        padding: 10px;
        font-size: 20px;
        outline: none;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
      }
    }
  }
  .review_container {
      flex: 1;
      height: 100%;
      
      display: flex;
      flex-direction: row;
      gap: 10px;      
      
      overflow-y: scroll;
      
      .review{
        height: 300px;
        min-width: 150px;
        border-radius: 10px;
        border-radius: 6px;
        background-color: rgba(217, 217, 217, 0.15);
        color: var(--color-ui-text-unfocused);
        padding: 10px;
        /* font-size: 20px; */
        outline: none;
        text-align: start;
        .review_top{
          display: flex;
          justify-content: space-between;
        }
    }
  }
</style>
