<script lang="ts">
  import { onMount } from "svelte";
  import type { PageData } from "./$types";
  import { getChat } from "$lib/chat";


  const getChat_list = async () => {
    const res = await getChat();
    console.log(res);
    return res;
  };

  onMount(async () => {
    setTimeout(async () => {
    console.log(await getChat());
      console.log("hello");
    }, 1000);
  });
</script>

<div class="chat">
  <div class="chat_list">
   <!--  {#await getChat()}
        <div>loading...</div>
    {:then chat}
        
            <div>{chat.user2}</div>        
        
    
    {:catch error}
        <div>{error.message}</div>
    {/await} -->

  </div>
  <div class="chat_panel"></div>
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
    background-color: blue;
    padding: 5%;
  }
  .chat_panel {
    flex: 3;
    background-color: red;
  }
</style>
