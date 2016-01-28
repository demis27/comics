import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {ComicBookListComponent} from './comicBookListComponent';
import {ComicBookDetailComponent} from './comicBookDetailComponent';
import {ComicBookService} from './comicBook.service'

@Component({
  selector: 'comics-app',
  template: `<nav>
                <a [routerLink]="['ComicBook']">Comic Books</a>
             </nav>
             <router-outlet></router-outlet>`,
  directives: [ROUTER_DIRECTIVES],
  providers: [ComicBookService]
})
@RouteConfig([
    {path:'/comicBook',        name: 'ComicBook',       component: ComicBookListComponent, useAsDefault: false},
    {path:'/comicBook/:id',    name: 'ComicBookDetail', component: ComicBookDetailComponent}
])
export class ComicBookComponent {
}
