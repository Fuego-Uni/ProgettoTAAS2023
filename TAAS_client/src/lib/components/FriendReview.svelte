<script lang="ts">
  import { getProfilePictureFriend } from "$lib/api/files";
  import { getFriendInfo } from "$lib/api/friends";
  import type { Review } from "$lib/types";
  import Rating from "./Rating.svelte";

  export let review: Review;
</script>


<div class="review ui-base">
  {#await getProfilePictureFriend(review.user) then src}
    {#if src}
      <img src={src} alt={review.user[0].toUpperCase()} class="avatar" />
    {:else}
      <div class="avatar">{review.user[0].toUpperCase()}</div>
    {/if}
  {/await}
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

    grid-template-columns: 2rem 1fr auto;
    grid-template-rows: 2rem 1fr;

    flex-direction: column;
    gap: 0.5rem;

    width: 100%;
    min-height: 10rem;
    max-height: 20rem;
  }

  .avatar {
    grid-column: 1;
    grid-row: 1;

    border-radius: 50%;

    display: flex;
    justify-content: center;
    align-items: center;

    background-color: var(--color-main-accent);
    color: white;

    font-size: 1.5rem;

    width: 100%;
    height: 100%;

    object-fit: cover;
  }

  .user {
    grid-column: 2;
    grid-row: 1;

    font-weight: bold;

    display: flex;
    align-items: center;

    font-size: 0.8rem;
    font-weight: normal;
  }

  .vote-wrap {
    grid-column: 3;
    grid-row: 1;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  .note {
    grid-column: 1 / 4;
    grid-row: 2;

    font-weight: normal;
  }
  
</style>