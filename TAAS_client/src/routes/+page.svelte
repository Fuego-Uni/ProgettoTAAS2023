<script lang="ts">
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";
  import { getFilmList } from "$lib/api/moviedb_api";
  import { getReviewedFilms } from "$lib/api/reviews";
</script>

<div class="page">
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
