<script lang="ts">
  import { onMount } from "svelte";
  import { addChat, addMessagetToChat, getChat, getMessagesByChatId } from "$lib/api/chat";
  import ChatPreview from "$lib/components/ChatPreview.svelte";
  import type { ChatType, MessageType } from "$lib/types";
  import ChatMessage from "$lib/components/ChatMessage.svelte";
  import { goto } from "$app/navigation";
  import { mainSocketSetHandler } from "$lib/SocketConnection";

  let add_user_input = "";
  let current_chat: string | null = null;

  let chat_list: ChatType[] = [];

  async function updateChatList() {
    chat_list = await getChat();
  }

  async function createNewChat() {
    addChat(add_user_input)
  }

  onMount(async () => {
    await updateChatList();

    mainSocketSetHandler("new-chat", updateChatList)
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="chat">
  <div class="chat_list">
    <div class="add-chat-container">
      <input bind:value={add_user_input} type="text" name="" id="" class="mail-input ui-interactive">
      <div class="add ui-interactive" on:click={createNewChat}>
        <svg xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/></svg>
      </div>
    </div>
    
    {#each chat_list as chat}
      <ChatPreview chat={chat} on:click={() => {goto(`/chat/${chat.id}`)}} />
    {/each}

  </div>
  <div class="chat_panel">
    <slot></slot>
  </div>
</div>

<style lang="scss">
  .chat {
    color: white;
    display: flex;
    height: 100%;
    width: 100%;
  }

  .chat_list {
    flex: 1;
    // background-color: blue;
    padding: 0.5rem;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;

    .add-chat-container {
      display: flex;
      align-items: center;
      height: 2rem;
      gap: 0.5rem;

      * {
        height: 100%;
      }

      .mail-input {
        outline: none;
        border: none;
        flex: 1;
      }
      .add {
        outline: none;
        border: none;
        width: 2rem;

        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

  .chat_panel {
    height: 100%;
    flex: 3;
    
    display: flex;
    flex-direction: column;
    padding: 0.5rem;
    gap: 0.5rem;

    .message-container {
      display: flex;
      flex-direction: column;
      height: 100%;

      gap: 0.5rem;

      overflow-y: scroll;
    }

    .chat-input-container {
      height: 2rem;
      display: flex;
      gap: 0.5rem;

      * {
        height: 100%;
      }

      .chat-input {
        outline: none;
        border: none;
        flex: 1;
      }
      .chat-send {
        outline: none;
        border: none;

        padding: 0 0.5rem;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

// MOBILE
@media (max-width: 950px) {
  .chat {
    display: flex;
    flex-direction: column;
    height: 100%;

    .chat_list {
      height: fit-content;
      flex: none;
    }

    .chat_panel {
      display: flex;
      flex-direction: column;
      width: 100%;
      // height: 100%;

      gap: 0.5rem;

      overflow-y: scroll;
    }
  }
}
</style>
