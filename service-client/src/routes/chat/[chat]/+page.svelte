<script lang="ts">
  import { onMount } from "svelte";
  import {
    addMessagetToChat,
    getMessagesByChatId,
  } from "$lib/api/chat";
  import type { MessageType } from "$lib/types";
  import ChatMessage from "$lib/components/ChatMessage.svelte";
  import type { PageData } from "./$types";
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import { goto } from "$app/navigation";

  export let data: PageData;

  let messages: MessageType[] = [];

  let file_name = "";
  function getFileName() {
    if (file_name == "") return "";

    let file_name_split = file_name.split("\\");
    return file_name_split[file_name_split.length - 1];
  }

  let files: FileList;
  let chat_text_input = "";
  async function sendMessage() {
    if (chat_text_input == "") return;

    let file = undefined;
    if (files) { file = files[0]; }

    addMessagetToChat(chat_text_input, data.chat_id, file);

    chat_text_input = "";
  }

  onMount(async () => {
    getMessagesByChatId(data.chat_id).then((res) => {
      if (res === undefined || res === null) {
        goto("/chat");
      } else {
        messages = res;
      }
    })

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
  <label class="file-input ui-interactive" for="upload">{file_name == "" ? "Upload File" : getFileName()}</label>
  <input class="file-input-hidden" id="upload" type="file" bind:value={file_name} bind:files={files}>
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

    .file-input {
      padding: 0 0.5rem;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .file-input-hidden {
      display: none;
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

// MOBILE
@media (max-width: 950px) {
  .message-container {
    padding-right: 0.5rem;
  }
}
</style>
