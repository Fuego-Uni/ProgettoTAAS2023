<div class="categories">
  <div class="category ui-interactive">Film</div>
  <div class="category ui-interactive">SerieTV</div>
  <div class="category ui-interactive">Musica</div>
  <div class="category ui-interactive">Libri</div>
</div>

<style lang="scss">
  .categories {
    height: 100%;

    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    gap: 0.3rem;

    padding: 0 1rem;

    .category {
      height: 2rem;

      display: flex;
      justify-content: center;
      align-items: center;
      width: 6rem;
      font-size: 0.9rem;

      border-radius: 0.3rem;
    }

    .category:first-child {
      border-top-left-radius: 1rem;
      border-bottom-left-radius: 1rem;
    }

    .category:last-child {
      border-top-right-radius: 1rem;
      border-bottom-right-radius: 1rem;
    }
  }
</style>
