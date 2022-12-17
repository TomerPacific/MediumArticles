import type { Article } from './article'


export function gatherMediumArticles(): Article[] {
    let articles: Article[]

    fetch('https://github.com/TomerPacific/MediumArticles/blob/master/README.md')
    .then(function(response) {
        return response.text()
    })
    .then(function(html) {
        console.log(html)
        return articles
    }).catch(function(e) {
        console.error(e)
        return []
    })

    return []
}