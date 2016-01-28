import {Injectable} from "angular2/core"
import {Http, Headers} from 'angular2/http';
import 'rxjs/Rx'

@Injectable()
export class ComicBookService{

    endpoint_url:String = "http://localhost:9090/comics-web-1.0-SNAPSHOT/api/rest/comicBook";

    constructor(http: Http){
        this.http = http;
    }

    getComicBooks (){
        var myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');
        return this.http.get(this.endpoint_url, { headers: myHeaders}).map(res => res.json());
    }

    addComicBook(title, isbn) {
        var myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');
        var body = '{"title": "' + title + '", "isbn": "' + isbn + '"}';
        console.log(body);
        return this.http.post(this.endpoint_url, body  ,{ headers: myHeaders}).map(res => res.json());
    }

    getComicBook(id: number) {
        var myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');
        return this.http.get(this.endpoint_url + "/" + id, { headers: myHeaders}).map(res => res.json());
    }
}