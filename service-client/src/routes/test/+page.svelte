<script lang="ts">
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { initiateAxios } from '$lib/authentication';
  import { postProfilePicture } from '$lib/api/files';

  let files: FileList;

  onMount(() => {
  });

  async function uploadFile() {
    initiateAxios();

    if (!files) { return; }

    const file = files[0];
    
    postProfilePicture(file);
  }

  async function loadProfile() {
    initiateAxios();

    const res = await axios.get('http://localhost:8080/storage/pfp', {
      responseType: 'blob' // tell axios to treat the response as a blob
    });

    const reader = new FileReader();
    reader.onloadend = function() {
      const img = document.querySelector('img');
      img!.src = reader.result as any; // reader.result contains the data URL
    }
    reader.readAsDataURL(res.data); // convert the blob to a data URL
  }

  async function listFiles() {
    initiateAxios();

    const res = await axios.get('http://localhost:8080/storage/');
  }
</script>

<div class="test">
  <input type="file" name="" id="" bind:files={files} >
  <button on:click={uploadFile}>Upload</button>
  <button on:click={loadProfile}>Load</button>
  <button on:click={listFiles}>List</button>
  <img alt="profile">
</div>


<style lang="scss">
  img {
    // width: 100px;
    // height: 100px;
  }
</style>

