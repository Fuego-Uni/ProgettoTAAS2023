<script lang="ts">
  import { onMount } from "svelte";
  import { getChat } from "$lib/chat";

  onMount(async () => {
    getChat().then((res) => {
      console.log(res);
    });
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
