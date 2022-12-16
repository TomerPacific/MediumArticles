export class Article {
    url:string;
    title: string;

    constructor(json: any) {
        this.url = json.url;
        this.title = json.title;
    }

}