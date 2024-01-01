<script lang="ts">
  import { mainSocketSetHandler } from "$lib/SocketConnection";
  import { getProfilePicture } from "$lib/api/files";
  import { addFriend, getFriendInfo, getFriendList, removeFriend } from "$lib/api/friends";
  import { fetchtUserInfo, updateUser } from "$lib/api/user";
  import FriendListElement from "$lib/components/FriendListElement.svelte";
  import { getUserInfo } from "$lib/store/user_info_store";
  import type { UserInfo } from "$lib/types";
  import axios from "axios";
  import { onMount } from "svelte";
  import toast from "svelte-french-toast";

  let user_info: UserInfo;

  let user_name = "Nome";
  let user_email = "Email";
  let profile_picture: string | null;

  let editing: boolean = false;
  let pfp_files: FileList | null = null;

  let old_user_name = "Nome";
  let old_user_email = "Email";
  let old_profile_picture: string | null;

  let friend_input = "";
  let friend_list: UserInfo[] = [];

  async function updateFriendList() {
    let friend_emails = await getFriendList();

    friend_list = await Promise.all(friend_emails.map(async (email) => {
      return await getFriendInfo(email);
    }));
  }

  async function updateProfilePicture(data: string) {
    let user = (await getUserInfo())!;

    if (user.email == data) {
      profile_picture = await getProfilePicture();
    }
  }

  async function updateUserInfo() {
    user_info = await fetchtUserInfo();

    user_name = user_info.name;
    user_email = user_info.email;
    old_user_name = user_info.name;
    old_user_email = user_info.email;
  }

  function editProfile() {
    editing = true;
    old_user_name = user_name;
    old_user_email = user_email;
    old_profile_picture = profile_picture;
  }

  function cancelEdit() {
    editing = false;
    user_name = old_user_name;
    user_email = old_user_email;

    if (old_profile_picture) profile_picture = old_profile_picture;
  }

  async function saveEdit() {
    editing = false;

    let pfp = pfp_files?.item(0) || undefined;

    updateUser(user_name, user_email, pfp);
  }

  onMount(async () => {
    await updateFriendList();
    await updateUserInfo();
    await updateProfilePicture(user_email);

    mainSocketSetHandler("friend-deleted", updateFriendList)
    mainSocketSetHandler("friend-added", updateFriendList)

    mainSocketSetHandler("pfp-updated", updateProfilePicture)
    mainSocketSetHandler("user-updated", updateUserInfo)

    mainSocketSetHandler("user-updated", () => {
      toast.success("User info updated");
    })
    mainSocketSetHandler("pfp-updated", () => {
      toast.success("User picture updated");
    })
  });
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-img-redundant-alt -->
<div class="page">
  {#if user_info}
    <div class="info-wrap wrap">
      <div class="avatar">
        {#if profile_picture}
          <img src={profile_picture} alt="profile picture">
        {:else}
          <div class="avatar">{user_info.name[0].toUpperCase()}</div>
        {/if}
        {#if editing}
          <label for="pfp" class="ui-interactive ui-icon">
            <svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"/></svg>
          </label>
          <input type="file" name="" id="pfp" class="hidden" accept="image/*" bind:files={pfp_files} />
        {/if}
      </div>
      <input disabled={!editing} type="text" name="" id="" class="name ui-interactive info" bind:value={user_name}>
      <input disabled={true} type="text" name="" id="" class="email ui-interactive info" bind:value={user_email}>
      <div class="edit-container">
        <button class="button ui-interactive" on:click={editProfile}>Edit</button>
        <div class="button ui-interactive" on:click={saveEdit} >Save</div>
        <div class="button ui-interactive" on:click={cancelEdit}>Cancel</div>
      </div>
    </div>
  {/if}
  <div class="friends-wrap wrap">
    <div class="add-friend-wrap ui-label-container">
      Add friend
      <div class="inputs">
        <input class="input ui-interactive" type="text" bind:value={friend_input} >
        <div class="button ui-interactive ui-icon" on:click={() => {addFriend(friend_input)}}><svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/></svg></div>
      </div>
    </div>
    <div class="friend-list-wrap ui-label-container">
      Friends:
      <div class="friend-list">
        {#each friend_list as friend}
          <FriendListElement userdata={friend} on:remove={(email) => {removeFriend(email.detail)}} />
        {/each}
      </div>
    </div>
  </div>
  <div class="activity-wrap wrap">

  </div>
</div>

<style lang="scss">
.page {
  height: 100%;
  width: 100%;

  display: flex;
  gap: 2rem;

  .wrap {
    height: 100%;
    padding: 1rem;
    gap: 1rem;

    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .info-wrap {
    width: 20rem;

    .avatar {
      height: 10rem;
      width: 10rem;
      border-radius: 50%;

      display: flex;
      justify-content: center;
      align-items: center;

      background-color: var(--color-main-accent);
      color: white;
      font-size: 5rem;
      position: relative;

      input {
        display: none;
      }

      label {
        position: absolute;
        right: 0;
        bottom: 0;
        width: 1.5rem;
        height: 1.5rem;
        border-radius: 50%;
      }

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 50%;
      }
    }

    .info {
      width: 100%;
      height: 2rem;
      align-items: center;
      padding: 0.2rem 0.5rem;

      border: none;
      outline: none;
      cursor: text;
    }

    .edit-container {
      width: 100%;
      display: flex;
      height: 2rem;
      gap: 0.5rem;

      .button {
        border: none;
        outline: none;
        flex: 1;
        display: flex;
        padding: 0.2rem 0.5rem;
        justify-content: center;
        align-items: center;
      }
    }
  }

  .friends-wrap {
    width: 20rem;
    gap: 2rem;

    .add-friend-wrap {
      width: 100%;

      .inputs {
        display: flex;
        gap: 0.2rem;
      }

      .button {
        width: 2rem;
        height: 2rem;
      }

      .input {
        width: 100%;
        height: 2rem;
        padding: 0.2rem 0.5rem;

        border: none;

        cursor: text;
      }      

      .input:focus { outline: none; }
    }

    .friend-list-wrap {
      width: 100%;

      .friend-list {
        width: 100%;
        height: 10rem;
        overflow-y: auto;

        display: flex;
        flex-direction: column;
        gap: 0.5rem;
      }
    }
  }

  .activity-wrap {
    flex: 1;
  }
}

@media (max-width: 950px) {
  .page {
    flex-direction: column;
    gap: 1rem;

    .wrap {
      width: 100%;
    }

    .info-wrap {
      width: 100%;
    }

    .friends-wrap {
      width: 100%;
    }

    .activity-wrap {
      width: 100%;
    }
  }
}
</style>