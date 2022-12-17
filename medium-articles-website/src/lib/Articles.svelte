<script lang="ts">
    import { onMount } from 'svelte';
    import { articles } from './AppStore';
    import { gatherMediumArticles } from './dataFetcher';

    onMount(() => {
        $articles = gatherMediumArticles()
    })
</script>

<main>
    <div id="grid-container">
        {#each $articles as article}
            <div class="grid-item">
                
            </div>
        {/each}
    </div>

</main>

<style>
    #grid-container {
        display: grid;
        grid: auto auto auto;
        grid-gap: 1%;
    }
</style>