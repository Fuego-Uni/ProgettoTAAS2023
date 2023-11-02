<script lang="ts">
  import type { UserInfo } from "$lib/types";
  import axios from "axios";
  import { createEventDispatcher } from "svelte";

  export let userdata: UserInfo;

  let dispatch = createEventDispatcher();
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<div class="friend-list-element">
  <div class="avatar ui-base">

  </div>
  <div class="name ui-base">
    {userdata.name}
  </div>
  <div class="remove ui-interactive ui-icon" on:click={() => {dispatch("remove", userdata.email)}}>
    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M432 256c0 17.7-14.3 32-32 32L48 288c-17.7 0-32-14.3-32-32s14.3-32 32-32l352 0c17.7 0 32 14.3 32 32z"/></svg>
  </div>
</div>

<style lang="scss">
.friend-list-element {
  width: 100%;
  height: 2rem;

  display: flex;
  align-items: center;
  gap: 0.2rem;

  .avatar {
    height: 100%;
    width: 2rem;
    border-radius: 50%;
  }

  .name {
    height: 100%;
    flex: 1;
    padding: 0.2rem 0.5rem;
  }

  .remove {
    height: 100%;
    width: 2rem;
  }
}
</style>