<script lang="ts">
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { getFilmInfo, getFilmList } from "$lib/api/moviedb_api";
  import { getFriendReviews, getReviewedMedia } from "$lib/api/reviews";
  import { onMount } from "svelte";
  import type { MediaData } from "$lib/types";
  import { mainSocketSetHandler } from "$lib/SocketConnection";

  let reviewed_films: MediaData[] = [];

  export async function updateReviewedFilmInfo() {
    let media = await getReviewedMedia("film");
    
    let media_info = await Promise.all(media.map(getFilmInfo));

    for(let i = 0; i < media_info.length; i++) {
      let reviews = await getFriendReviews(media_info[i].id, "film");

      let average = 0;
      for(let j = 0; j < reviews.length; j++) {
        average += reviews[j].vote;
      }

      average /= reviews.length;

      media_info[i].rating_average = average;
    }

    reviewed_films = media_info;
  }

  onMount(async () => {
    updateReviewedFilmInfo();
    
    mainSocketSetHandler("review-added", updateReviewedFilmInfo)
    mainSocketSetHandler("friend-added", updateReviewedFilmInfo)
    mainSocketSetHandler("friend-deleted", updateReviewedFilmInfo)
  });
</script>

<div class="page">
  <ContentCarousel title="Friends recommend" items={reviewed_films} type={"film"} />
  {#await getFilmList(1, 'popular') then films}
    <ContentCarousel title="Popular" items={films} type={"film"} />
  {/await}
  {#await getFilmList(1, 'upcoming') then films}
    <ContentCarousel title="Upcoming" items={films} type={"film"} />
  {/await}
  {#await getFilmList(1, 'top_rated') then films}
    <ContentCarousel title="Top Rated" items={films} type={"film"} />
  {/await}
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
      width: 100%;

      padding: 1rem;
      padding-top: 0;
      margin-top: 1rem;
      overflow-y: scroll;
    }
  }
</style>
