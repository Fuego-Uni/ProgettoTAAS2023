<script lang="ts">
</script>

<div class="search">
  <div class="search-bar ui-base">
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512"
      ><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path
        d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"
      /></svg
    >
    <input type="text" placeholder="Cerca..." />
  </div>
</div>

<style lang="scss">
  .search {
    height: 100%;
    width: 100%;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  .search-bar {
    height: 2rem;
    width: 15rem;

    display: flex;
    justify-content: center;
    align-items: center;

    border-radius: 1rem;
    padding: 0 0.5rem;

    svg {
      height: 100%;
      width: 1rem;
    }

    input {
      background-color: transparent;
      padding: 0.5rem;
      height: 100%;
      width: 100%;

      border: none;
      outline: none;

      border-radius: 0.3rem;

      color: var(--color-main-text);
    }

    input::placeholder {
      color: var(--color-main-text);
    }
  }

// MOBILE
@media (max-width: 950px) {
  .search-bar:not(.active) {
    width: 2rem;
    height: 2rem;

    input {
      display: none;
    }
  }
}
</style>
