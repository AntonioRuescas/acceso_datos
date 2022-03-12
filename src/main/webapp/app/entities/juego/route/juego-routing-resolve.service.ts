import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJuego, Juego } from '../juego.model';
import { JuegoService } from '../service/juego.service';

@Injectable({ providedIn: 'root' })
export class JuegoRoutingResolveService implements Resolve<IJuego> {
  constructor(protected service: JuegoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJuego> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((juego: HttpResponse<Juego>) => {
          if (juego.body) {
            return of(juego.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Juego());
  }
}
