<script lang="ts">
  import '$lib/styles/main.scss'

  import { initiateAxios } from '$lib/authentication';
  import Categories from '$lib/components/Categories.svelte';
  import Search from '$lib/components/Search.svelte';
  import Sidebar from '$lib/components/Sidebar.svelte';
  import { onMount } from 'svelte';
  import { goto } from '$app/navigation';
  import { page } from '$app/stores';

  onMount(() => {
    initiateAxios();

    let url = $page.url.toString();
    if(localStorage.getItem('auth-token') === null && !url.includes('auth/signup')) {
      goto('auth/signup')
    }
  });
</script>

<div class="page">
  <div class="logo-wrapper">
    <div class="logo">
      Notflix
    </div>
  </div>
  <div class="categories-wrapper">
    <Categories></Categories>
  </div>
  <div class="search-wrapper">
    <Search></Search>
  </div>
  <div class="sidebar-wrapper">
    <Sidebar></Sidebar>
  </div>
  <div class="content-wrapper">
    <slot></slot>
  </div>
</div>

<style lang="scss">
  .page {
    display: grid;
    grid-template-areas: 
      "logo logo categories search"
      "sidebar content content content";

    grid-template-columns: 4rem 10rem 1fr 20rem;
    grid-template-rows: 4rem calc(100vh - 4rem);

    height: 100vh;
    width: 100vw;

    .logo-wrapper {
      grid-area: logo;

      display: flex;
      justify-content: center;
      height: 4rem;

      .logo {
        width: 100%;
        height: 100%;

        font-family: 'Anton', sans-serif;
        font-size: 2.5rem;
        background-color: var(--color-main-accent);
        color: var(--color-main-text);

        display: flex;
        justify-content: center;
        align-items: center;

        border-radius: 0 0 0.5rem 0;
      }
    }
    
    .categories-wrapper {
      grid-area: categories;
    }

    .search-wrapper {
      grid-area: search;
      padding-right: 1rem;
    }

    .sidebar-wrapper {
      grid-area: sidebar;
    }

    .content-wrapper {
      grid-area: content;

      height: calc(100vh - 4rem);
      width: calc(100vw - 4rem);
    }
  }


// MOBILE
@media (max-width: 950px) {
  .page {
    grid-template-areas: 
      "sidebar logo search"
      "categories categories categories"
      "content content content";
    grid-template-columns: 4rem 1fr 4rem;
    grid-template-rows: 4rem 2rem 1fr;

    .sidebar-wrapper {
      padding-left: 1rem;
    }

    .search-wrapper {
    }

    .logo-wrapper {
      .logo {
        background-color: transparent;
        color: var(--color-main-accent);
      }
    }

    .content-wrapper {
      height: calc(100vh - 4rem - 2rem);
      width: 100vw;
    }
  }
}
</style>