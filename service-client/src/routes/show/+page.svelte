<script lang="ts">
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { getFilmInfo, getFilmList, getShowInfo, getShowList } from "$lib/api/moviedb_api";
  import { getFriendReviews, getReviewedMedia } from "$lib/api/reviews";
  import { onMount } from "svelte";
  import type { MediaData } from "$lib/types";
  import { mainSocketSetHandler } from "$lib/SocketConnection";

  let reviewed_shows: MediaData[] = [];

  export async function updateReviewedFilmInfo() {
    let media = await getReviewedMedia("show");
    
    let media_info = await Promise.all(media.map(getShowInfo));

    for(let i = 0; i < media_info.length; i++) {
      let reviews = await getFriendReviews(media_info[i].id, "show");

      let average = 0;
      for(let j = 0; j < reviews.length; j++) {
        average += reviews[j].vote;
      }

      average /= reviews.length;

      media_info[i].rating_average = average;
    }

    reviewed_shows = media_info;
  }

  onMount(async () => {
    updateReviewedFilmInfo();
    
    mainSocketSetHandler("review-added", updateReviewedFilmInfo)
    mainSocketSetHandler("friend-added", updateReviewedFilmInfo)
    mainSocketSetHandler("friend-deleted", updateReviewedFilmInfo)
  });
</script>

<div class="page">
  <ContentCarousel title="Friends recommend" items={reviewed_shows} type={"show"} />
  {#await getShowList(1, 'top_rated') then shows}
    <ContentCarousel title="Top Rated" items={shows} type={"show"} />
  {/await}
  {#await getShowList(1, 'popular') then shows}
    <ContentCarousel title="Popular" items={shows} type={"show"} />
  {/await}
  {#await getShowList(1, 'airing_today') then shows}
    <ContentCarousel title="Airing Today" items={shows} type={"show"} />
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
