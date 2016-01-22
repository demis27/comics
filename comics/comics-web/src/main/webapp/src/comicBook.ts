import {Component} from 'angular2/core';
import {ComicBookService} from './comicBook.service'

@Component({
  selector: 'comics-app',
  templateUrl: 'src/comicBook.template.html',
  providers: [ComicBookService]
})
export class ComicBookComponent {
    constructor(private _comicBookService: ComicBookService){
        this._comicBookService = _comicBookService;
        this.book = {title: "", isbn: ""};
    }

    getComicBooks(){
        this.comicBooks = [];
        this._comicBookService.getComicBooks()
         .subscribe(
            data => this.comicBooks = data,
            error => console.log("error");
         );
    }

    addComicBook() {
        this._comicBookService.addComicBook(this.book.title, this.book.isbn).subscribe(
                                                          data =>  this.getComicBooks();,
                                                          error => console.log("error");
                                                       );
    }


    ngOnInit(){
            console.log('init ComicBookList');
            this.getComicBooks();
            console.log(this.comicBooks[0]);
        }
}