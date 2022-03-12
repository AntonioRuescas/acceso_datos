import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartida, Partida } from '../partida.model';
import { PartidaService } from '../service/partida.service';

@Injectable({ providedIn: 'root' })
export class PartidaRoutingResolveService implements Resolve<IPartida> {
  constructor(protected service: PartidaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartida> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partida: HttpResponse<Partida>) => {
          if (partida.body) {
            return of(partida.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Partida());
  }
}
