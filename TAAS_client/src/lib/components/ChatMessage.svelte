<script lang="ts">
  import { getImageById } from "$lib/api/files";
import { getUserInfo } from "$lib/store/user_info_store";
  import type { MessageType } from "$lib/types";
  import { onMount } from "svelte";

  export let message: MessageType;

  let is_of_current_user = false;

  function getDate() {
    let date = new Date(message.timestamp);
    let hours = date.getHours();
    let minutes = date.getMinutes();

    let minutes_str = minutes < 10 ? "0" + minutes : minutes;

    return hours + ":" + minutes_str;
  }

  onMount(async () => {
    let current_user = (await getUserInfo())!;

    is_of_current_user = message.email == current_user.email;
  });
</script>

<div class={`chat-message ui-base ${is_of_current_user ? 'self' : ''}`}>
  <div class="chat-message__text">
    {message.content}
  </div>
  {#if message.image}
    {#await getImageById(message.image)}
      <div class="loading">loading</div>
    {:then image} 
      {#if image == null}
        <div class="error">unable to load image</div>
      {:else}
        <img class="image" src={image} alt="" />
      {/if}
    {/await}
  {/if}
  <div class="date">
    {getDate()}
  </div>
</div>

<style lang="scss">
  .chat-message {
    width: fit-content;
    max-width: 60%;

    display: flex;
    flex-direction: column;

    gap: 0.2rem;

    .image {
      width: 30rem;
      height: 100%;
      object-fit: contain;
      border-radius: 0.1rem;
    }

    .error {
      color: rgb(255, 67, 67);
      font-size: 0.7rem;
    }

    .date {
      font-size: 0.7rem;
      color: #aaa;
      align-self: flex-end;
    }
  }

  .chat-message.self {
    align-self: flex-end;
  }
</style>