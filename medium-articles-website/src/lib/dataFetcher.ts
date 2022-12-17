import type { Article } from './article'

export function gatherMediumArticles(): Article[] {
    let articles: Article[]

    fetch('https://raw.githubusercontent.com/tomerpacific/MediumArticles/master/README.md')
    .then(function(response) {
        return response.text()
    })
    .then(function(html) {
        console.log(html)
        return articles
    }).catch(function(e) {
        return []
    })

    return []
}