<script lang="ts">
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import { getProfilePictureFriend } from "$lib/api/files";
  import { getFriendInfo } from "$lib/api/friends";
  import { getUserInfo } from "$lib/store/user_info_store";
  import type { ChatType } from "$lib/types";
  import { createEventDispatcher, onMount } from "svelte";

  const dispatch = createEventDispatcher();

  export let chat: ChatType;
  let display_name: string = " ";
  let other_user = " ";

  let profile_picture: string | null;

  async function updateProfilePicture(data: string) {
    console.log(data, other_user);
    
    if (other_user == data) {
      console.log(data)
      profile_picture = await getProfilePictureFriend(data);
    }
  }

  onMount(async () => {
    let current_user = (await getUserInfo())!;

    let other = chat.user1 == current_user.email ? chat.user2 : chat.user1;

    getFriendInfo(other).then((res) => {
      other_user = res.email;
      display_name = res.name;
      updateProfilePicture(other);
    });

    mainSocketSetHandler("pfp-updated", updateProfilePicture);
  });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-img-redundant-alt -->
<div class="chat-preview ui-interactive" on:click={() => (dispatch("click"))}>
  <div class="chat-preview__avatar">
    {#if profile_picture}
      <img src={profile_picture} class="avatar" alt="profile picture">
    {:else}
      <div class="avatar">{other_user[0].toUpperCase()}</div>
    {/if}
  </div>
  <div class="chat-preview__user">
    {display_name}
  </div>
</div>

<style lang="scss">
  .chat-preview {
    width: 100%;
    height: 2.5rem;

    display: flex;
    align-items: center;
    justify-content: flex-start;
    gap: 0.2rem;
    padding-left: 0.2rem;

    .avatar {
      height: 2rem;
      width: 2rem;
      border-radius: 50%;

      display: flex;
      justify-content: center;
      align-items: center;

      background-color: var(--color-main-accent);
      color: white;
      font-size: 1.2rem;
      position: relative;
    }

    &__user {
      flex-grow: 1;
    }
  }
</style>