import {Component, Inject, Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS, httpInjectables, BaseRequestOptions} from 'angular2/http';

export class ComicBook {

    constructor(_id: String, _title: String) {this.title = _title; this.id= _id};

    title: String;
    id: String;
}

@Injectable()
export class ComicBookService {
    http: Http;
    baseURL: string;
    baseRequestOptions: BaseRequestOptions;

    constructor(@Inject(Http) http, @Inject(BaseRequestOptions) baseRequestOptions) {
        this.http = http;
        this.baseURL = '/api/rest/comicBook/';
        this.baseRequestOptions = baseRequestOptions;
    };

    _callAPI(url:string, method:string, data:any) {
        return window.fetch(url, {
            method: method,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
    }

    getComicBooks() {
        var result: ComicBook[] = [new ComicBook('1', 'Le combat Ordinaire')];
        console.log(this.http.get(this.baseURL));
        return result;
    }
}

export class Logger {
  log(msg: any)   { console.log(msg); }
  error(msg: any) { console.error(msg); }
  warn(msg: any)  { console.warn(msg); }
}

@Component({
  selector: 'comicbook-list',
  templateUrl: 'app/comicbook-list.html',
  providers: [ComicBookService, Logger, HTTP_PROVIDER]
})
export class ComicBookList implements OnInit {
    constructor(private _service: ComicBookService, private _logger: Logger){
        this._logger.log('construct ComicBookList');
    }
    comicBooks: ComicBook[];

    ngOnInit(){
        this._logger.log('init ComicBookList');
        this.comicBooks = this._service.getComicBooks();
        this._logger.log(this.comicBooks[0]);
    }
}


