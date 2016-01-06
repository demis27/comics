import {Component} from 'angular2/core';
import {Http, HTTP_PROVIDERS} from 'angular2/http';

@Component({
    selector: 'backend',
    providers: [HTTP_PROVIDERS],
})
export class Backend {
    constructor(http:Http) {};

    get() {
        http.get('/api/rest/comicBook/').subscribe(res => return res.json());
    }
}
