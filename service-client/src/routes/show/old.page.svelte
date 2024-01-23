<script lang="ts">
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { getShowList } from "$lib/api/moviedb_api";
  import { getReviewedShows } from "$lib/api/reviews";
</script>

<div class="page">
  {#await getReviewedShows() then films}
    <ContentCarousel title="Friends recommend" items={films} type={"show"} />
  {/await}
  {#await getShowList(1, 'top_rated') then films}
    <ContentCarousel title="Top Rated" items={films} type={"show"} />
  {/await}
  {#await getShowList(1, 'popular') then films}
    <ContentCarousel title="Popular" items={films} type={"show"} />
  {/await}
  {#await getShowList(1, 'airing_today') then films}
    <ContentCarousel title="Airing Today" items={films} type={"show"} />
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

  // MOBILE
  @media (max-width: 950px) {
    .page {
      padding: 1rem;
      padding-top: 0;
      margin-top: 1rem;
      overflow-y: scroll;
    }
  }
</style>
