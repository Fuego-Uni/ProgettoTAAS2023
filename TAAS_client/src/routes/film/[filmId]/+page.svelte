<script lang="ts">
    import type { PageData } from './$types';
    import { reviewFilm } from '$lib/film';
    export let data: PageData;
    console.log(data.film_id);

    let film_id = data.film_id.toString();
    function handleReview() {
        reviewFilm(Number(data.film_id), "testtesttest", "notenotenotenote");
    }
</script>

<div class="film">
    <button on:click={handleReview}> test</button>
</div>