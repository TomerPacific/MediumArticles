import { Article } from './article'

const ENDPOINT: string = 'https://raw.githubusercontent.com/tomerpacific/MediumArticles/master/README.md'
const ARTICLE_TITLE_INDEX: number = 1;
const ARTICLE_LINK_INDEX: number = 2;

export function gatherMediumArticles(): Promise<Article[]> {
    let articles: Article[] = []
    return new Promise((resolve, reject) => {
        fetch(ENDPOINT)
        .then(function(response) {
            return response.text()
        })
        .then(function(html) {
            const regexp = new RegExp(/\[(.*?)\]\((.*?)\)/, 'g')
            let matches:RegExpExecArray

            while ((matches = regexp.exec(html)) !== null) {
                let articleTitle = matches[ARTICLE_TITLE_INDEX]
                let articleLink = matches[ARTICLE_LINK_INDEX]
                let article: Article = new Article(articleTitle, articleLink)
                articles.push(article);
            }
            
            return resolve(articles)
        }).catch(function(e) {
            return reject(e)
        })
    })
}