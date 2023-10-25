<script lang="ts">
  import ContentPreview from "$lib/components/ContentPreview.svelte";
  import TestBanner from '$lib/assets/testimg.png'
  import ContentCarousel from "$lib/components/ContentCarousel.svelte";

  let large_active = 0;
  let small_active = 0;
</script>

<div class="page">
  <div class="carousel-wrapper">
    <ContentCarousel title="featured" items={[
      {banner: TestBanner, title: "Test Title ma molto lungo", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title lungo", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
    ]} />
  </div>
  <div class="carousel-wrapper">
    <ContentCarousel title="titolo 2" items={[
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
    ]} />
  </div>
  <div class="carousel-wrapper">
    <ContentCarousel title="titolo 3" items={[
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
      // {banner: TestBanner, title: "Test Title", date: "2021", rating_average: 4, rating_personal: 3},
    ]} />
  </div>
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