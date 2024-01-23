<script lang="ts">
  import { createEventDispatcher } from "svelte";

  export let rating_average: number = 0;
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<div class="rating">
  {Math.round(rating_average * 10) / 10}
</div>

<style lang="scss">
  .rating {
    display: flex;
    align-items: center;
    justify-content: center;

    width: 2.5rem;
    height: 2.5rem;

    border-radius: 50%;
    background-color: rgba(0, 0, 0, 0.5);

    font-size: 1rem;
    font-weight: 600;
    color: white;

    border: 3px solid rgb(0, 149, 255);
  }
</style>
