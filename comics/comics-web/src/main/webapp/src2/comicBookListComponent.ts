import {Component, OnInit} from 'angular2/core';
import {Router} from 'angular2/router';
import {ComicBookService} from './comicBook.service';
import {ComicBookDetailComponent} from './comicBookDetailComponent';
import {ComicBook} from './comicBook';


@Component({
  selector: 'comics-app-list',
  templateUrl: 'src2/comicBookList.template.html',
  providers: [ComicBookService],
  directives: [ComicBookDetailComponent]
})
export class ComicBookListComponent {
    public selectedBook: ComicBook;


    constructor(private _comicBookService: ComicBookService, private _router: Router){
    }

    getComicBooks(){
        this.comicBooks = [];
        this._comicBookService.getComicBooks()
         .subscribe(
            data => this.comicBooks = data,
            error => console.log("error");
         );
    }

    ngOnInit(){
            this.getComicBooks();
    }

    gotoDetail() {
        this._router.navigate(['ComicBookDetail', { id: this.selectedBook.id }]);
    }

    onSelect(comicBook: ComicBook) { this.selectedBook = comicBook; }

}
