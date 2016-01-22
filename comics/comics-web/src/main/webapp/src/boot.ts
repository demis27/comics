import {bootstrap} from 'angular2/platform/browser';
import {HTTP_PROVIDERS} from 'angular2/http';

import {ComicBookComponent} from './comicBook';

bootstrap(ComicBookComponent, [HTTP_PROVIDERS]);