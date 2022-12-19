import { Article } from './article'

export function gatherMediumArticles(): Promise<Article[]> {
    let articles: Article[] = []
    return new Promise((resolve, reject) => {
        fetch('https://raw.githubusercontent.com/tomerpacific/MediumArticles/master/README.md')
        .then(function(response) {
            return response.text()
        })
        .then(function(html) {
            const regexp = new RegExp(/\[(.*?)\]\((.*?)\)/, 'g')
            let matches
            while ((matches = regexp.exec(html)) !== null) {
                let articleTitle = matches[1]
                let articleLink = matches[2]
                let article: Article = new Article(articleTitle, articleLink)
                articles.push(article);
            }
            return resolve(articles)
        }).catch(function(e) {
            return reject(e)
        })
    })
}