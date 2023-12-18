<script lang="ts">
  import { getFriendInfo } from "$lib/api/friends";
  import { getUserInfo } from "$lib/store/user_info_store";
  import type { Review } from "$lib/types";
  import Rating from "./Rating.svelte";


  export let review: Review;
</script>


<div class="review ui-base">
  {#await getFriendInfo(review.user) then user}
    <div class="user">{user.name}</div>
  {/await}
  <!-- <div class="user">{review.user}</div> -->
  <div class="vote-wrap">
    <Rating rating_average={review.vote} />
  </div>
  <div class="note">{review.note}</div>
</div>

<style lang="scss">
  .review {
    display: grid;

    grid-template-columns: 1fr auto;
    grid-template-rows: 2rem 1fr;

    flex-direction: column;
    gap: 0.5rem;

    width: 100%;
    min-height: 10rem;
    max-height: 20rem;
  }

  .user {
    grid-column: 1;
    grid-row: 1;

    font-weight: bold;

    display: flex;
    align-items: center;

    font-size: 0.8rem;
    font-weight: normal;
  }

  .vote-wrap {
    grid-column: 2;
    grid-row: 1;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  .note {
    grid-column: 1 / 3;
    grid-row: 2;

    font-weight: normal;
  }
  
</style>