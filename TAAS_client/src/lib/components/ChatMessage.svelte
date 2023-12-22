<script lang="ts">
  import { getUserInfo } from "$lib/store/user_info_store";
  import type { MessageType } from "$lib/types";
  import { onMount } from "svelte";

  export let message: MessageType;

  let is_of_current_user = false;

  let date = new Date(message.timestamp).getHours() + ":" + new Date(message.timestamp).getMinutes();

  onMount(async () => {
    let current_user = (await getUserInfo())!;

    is_of_current_user = message.email == current_user.email;
  });
</script>

<div class={`chat-message ui-base ${is_of_current_user ? 'self' : ''}`}>
  <div class="chat-message__text">
    {message.content}
  </div>
  <div class="date">
    {date}
  </div>
</div>

<style lang="scss">
  .chat-message {
    width: fit-content;
    max-width: 60%;

    display: flex;
    flex-direction: column;

    gap: 0.2rem;

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