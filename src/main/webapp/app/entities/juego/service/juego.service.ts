import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJuego, getJuegoIdentifier } from '../juego.model';

export type EntityResponseType = HttpResponse<IJuego>;
export type EntityArrayResponseType = HttpResponse<IJuego[]>;

@Injectable({ providedIn: 'root' })
export class JuegoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/juegos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(juego: IJuego): Observable<EntityResponseType> {
    return this.http.post<IJuego>(this.resourceUrl, juego, { observe: 'response' });
  }

  update(juego: IJuego): Observable<EntityResponseType> {
    return this.http.put<IJuego>(`${this.resourceUrl}/${getJuegoIdentifier(juego) as number}`, juego, { observe: 'response' });
  }

  partialUpdate(juego: IJuego): Observable<EntityResponseType> {
    return this.http.patch<IJuego>(`${this.resourceUrl}/${getJuegoIdentifier(juego) as number}`, juego, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJuego>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJuego[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addJuegoToCollectionIfMissing(juegoCollection: IJuego[], ...juegosToCheck: (IJuego | null | undefined)[]): IJuego[] {
    const juegos: IJuego[] = juegosToCheck.filter(isPresent);
    if (juegos.length > 0) {
      const juegoCollectionIdentifiers = juegoCollection.map(juegoItem => getJuegoIdentifier(juegoItem)!);
      const juegosToAdd = juegos.filter(juegoItem => {
        const juegoIdentifier = getJuegoIdentifier(juegoItem);
        if (juegoIdentifier == null || juegoCollectionIdentifiers.includes(juegoIdentifier)) {
          return false;
        }
        juegoCollectionIdentifiers.push(juegoIdentifier);
        return true;
      });
      return [...juegosToAdd, ...juegoCollection];
    }
    return juegoCollection;
  }
}
