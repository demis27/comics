import {Component, OnInit} from 'angular2/core';
import {RouteParams} from 'angular2/router';
import {ComicBookService} from './comicBook.service';
import {ComicBook} from './comicBook';

@Component({
    selector: 'comics-app-detail',
    templateUrl: 'src2/comicBookDetail.template.html',
    providers: [ComicBookService],
    inputs: ['comicBook']
})
export class ComicBookDetailComponent {
    constructor(private _comicBookService: ComicBookService, private _routeParams: RouteParams){
    }

    public comicBook: ComicBook;

    ngOnInit() {
        if (!this.comicBook) {
          let id = +this._routeParams.get('id');
          this._comicBookService.getComicBook(id).subscribe(
                                                            data => this.comicBook = data,
                                                            error => console.log("error");
                                                           );

        }
    }

    goBack() {
        window.history.back();
    }
}
