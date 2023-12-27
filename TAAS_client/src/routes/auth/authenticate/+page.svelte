<script lang="ts">
  import { goto } from "$app/navigation";
  import { setAuthenticationToken } from "$lib/authentication";
  import { onMount } from "svelte";

  onMount(() => {
    // get url params
    const urlParams = new URLSearchParams(window.location.search);
    // get "code" param
    const code = urlParams.get("code");

    setAuthenticationToken(code);

    goto("/profile")
  });
</script>