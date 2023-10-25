<script lang="ts">
  import type { ContentPreviewData } from "$lib/types";
  import ContentPreview from "./ContentPreview.svelte";

  export let items: ContentPreviewData[]
  export let title: string

  let active = 0;
</script>

<div class="content-carousel">
  <div class="title">{title}</div>
  <div class="list">
    {#each items as item, i}
      <ContentPreview data={item} active={i==active} on:click={() => {active=i}} />
    {/each}
  </div>
</div>

<style lang="scss">
  .content-carousel {
    display: flex;
    flex-direction: column;
    height: 100%;
    gap: 0.5rem;

    color: var(--color-ui-text-focused);
    font-size: 1.5rem;

    
    .title {
      text-shadow: rgba(255,255,255,0.6) 0px 0px 20%; 
    }

    .list {
      display: flex;
      gap: 1rem;
    }
  }

  // MOBILE
@media (max-width: 950px) {
  .content-carousel {
    .list {
      flex-direction: column;
    }
  }
}
</style>