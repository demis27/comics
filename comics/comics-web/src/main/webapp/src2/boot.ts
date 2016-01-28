import {bootstrap} from 'angular2/platform/browser';
import {HTTP_PROVIDERS, ROUTER_DIRECTIVES} from 'angular2/http';
import {ROUTER_PROVIDERS} from 'angular2/router';

import {ComicBookComponent} from './comicBookComponent';

bootstrap(ComicBookComponent, [HTTP_PROVIDERS, ROUTER_PROVIDERS]);