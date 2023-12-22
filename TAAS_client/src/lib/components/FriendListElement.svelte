<script lang="ts">
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import { getProfilePicture, getProfilePictureFriend } from "$lib/api/files";
  import { getUserInfo } from "$lib/store/user_info_store";
  import type { UserInfo } from "$lib/types";
  import axios from "axios";
  import { createEventDispatcher, onMount } from "svelte";

  let dispatch = createEventDispatcher();

  export let userdata: UserInfo;

  let profile_picture: string | null;

  async function updateProfilePicture(data: string) {
    if (userdata.email == data) {
      profile_picture = await getProfilePictureFriend(data);
    }
  }

  onMount(async () => {
    mainSocketSetHandler("pfp-updated", updateProfilePicture)

    updateProfilePicture(userdata.email);
  });
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-img-redundant-alt -->
<div class="friend-list-element">
  <div class="avatar">
    {#if profile_picture}
      <img src={profile_picture} class="avatar" alt="profile picture">
    {:else}
      <div class="avatar">{userdata.name[0].toUpperCase()}</div>
    {/if}
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
    height: 2rem;
    width: 2rem;
    border-radius: 50%;

    padding: 0;
    overflow: hidden;

    display: flex;
    justify-content: center;
    align-items: center;

    background-color: var(--color-main-accent);
    color: white;
    font-size: 1.5rem;

    img {
      height: 100%;
      width: 100%;
      object-fit: cover;
    }
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