export class Article {
    url:string;
    title: string;

    constructor(title: string, link: string) {
        this.url = link;
        this.title = title;
    }

}