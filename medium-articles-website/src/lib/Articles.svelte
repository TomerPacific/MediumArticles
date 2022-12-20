<script lang="ts">
    import { onMount } from 'svelte';
    import { articles } from './AppStore';
    import { gatherMediumArticles } from './dataFetcher';

    onMount(() => {
        gatherMediumArticles().then(function(data) {
            $articles = data
        })
    })
</script>

<main>
    <div id="grid-container">
        {#each $articles as article}
            <div class="grid-item">
                <h3>
                    <a href="{article.url}" title="{article.title}">{article.title}</a>
                </h3>
            </div>
        {/each}
    </div>

</main>

<style>
    #grid-container {
        display: grid;
        grid-template-columns: auto auto auto;
        grid-gap: 1%;
    }

    .grid-item {
        border: 2px solid black;
        margin: 1%;
    }
</style>