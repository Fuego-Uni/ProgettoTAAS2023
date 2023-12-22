<script lang="ts">
  import { onMount } from "svelte";
  import {
    addChat,
    addMessagetToChat,
    getChat,
    getMessagesByChatId,
  } from "$lib/api/chat";
  import type { MessageType } from "$lib/types";
  import ChatMessage from "$lib/components/ChatMessage.svelte";
  import type { PageData } from "./$types";
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import toast from "svelte-french-toast";

  export let data: PageData;

  let messages: MessageType[] = [];

  let chat_text_input = "";
  async function sendMessage() {
    if (chat_text_input == "") return;

    addMessagetToChat(chat_text_input, data.chat_id);

    chat_text_input = "";
  }

  onMount(async () => {
    getMessagesByChatId(data.chat_id).then((res) => {
      messages = res;
    });

    mainSocketSetHandler("new-message", (chat_id: string) => {
      if (chat_id != data.chat_id) return;

      getMessagesByChatId(chat_id).then((res) => {
        messages = res;
      });
    });


  });
</script>

<div class="message-container">
  {#each messages as message}
    <ChatMessage {message} />
  {/each}
</div>
<div class="chat-input-container">
  <input
    type="text"
    name=""
    id=""
    class="chat-input ui-interactive"
    bind:value={chat_text_input}
  />
  <div class="chat-send ui-interactive" on:click={sendMessage}>Send</div>
</div>

<style lang="scss">
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
</style>
