<script lang="ts">
  import GoogleIcon from '$lib/assets/googleicon.png'
</script>

<div class="page">
  <form class="form" method="post" action="?/OAuth2">
    <!-- <svg class="icon" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 488 512">! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc.<path d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"/></svg> -->
    <img class="icon" src={GoogleIcon} />
    <button class="btn-auth" type="submit">
      Sign up with Google
    </button>
  </form>
</div>

<style lang="scss">
.page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;

  .form {
    background-color: #4284f0;
    border-radius: 1px;

    overflow: hidden;
    

    &:hover {
      background-color: #3367d6;
    }

    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }

  .icon {
    fill: white;
    margin: 2px;
    padding: 2px;

    height: calc(2.5rem - 4px);
    aspect-ratio: 1/1;

    border-radius: 2px;

    background-color: white;
  }

  .btn-auth {
    height: 2.5rem;
    width: 10rem;

    border: none;

    color: white;
    font-weight: bold;
    cursor: pointer;

    background-color: transparent;
  }
}
</style>
