<script lang="ts">
  import { addFriend, getFriendInfo, getFriendList, removeFriend } from "$lib/api/friends";
  import { initiateAxios } from "$lib/authentication";
  import FriendListElement from "$lib/components/FriendListElement.svelte";
  import type { UserInfo } from "$lib/types";
  import axios from "axios";
  import { onMount } from "svelte";

  let user_name = "Nome";
  let user_email = "Email";

  let friend_input = "";

  let friend_list: UserInfo[] = [];

  onMount(async () => {
    initiateAxios();
    let friend_emails = await getFriendList();

    friend_list = await Promise.all(friend_emails.map(async (email) => {
      return await getFriendInfo(email);
    }));
  });
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<div class="page">
  <div class="info-wrap wrap">
    <div class="avatar">

    </div>
    <div class="name ui-base info">
      {user_name}
    </div>
    <div class="email ui-base info">
      {user_email}
    </div>
  </div>
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
      background-color: white;
      height: 10rem;
      width: 10rem;
      border-radius: 50%
    }

    .info {
      width: 100%;
      height: 2rem;
      align-items: center;
      padding: 0.2rem 0.5rem;
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