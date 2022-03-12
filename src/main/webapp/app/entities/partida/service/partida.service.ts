import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartida, getPartidaIdentifier } from '../partida.model';

export type EntityResponseType = HttpResponse<IPartida>;
export type EntityArrayResponseType = HttpResponse<IPartida[]>;

@Injectable({ providedIn: 'root' })
export class PartidaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/partidas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partida: IPartida): Observable<EntityResponseType> {
    return this.http.post<IPartida>(this.resourceUrl, partida, { observe: 'response' });
  }

  update(partida: IPartida): Observable<EntityResponseType> {
    return this.http.put<IPartida>(`${this.resourceUrl}/${getPartidaIdentifier(partida) as number}`, partida, { observe: 'response' });
  }

  partialUpdate(partida: IPartida): Observable<EntityResponseType> {
    return this.http.patch<IPartida>(`${this.resourceUrl}/${getPartidaIdentifier(partida) as number}`, partida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartidaToCollectionIfMissing(partidaCollection: IPartida[], ...partidasToCheck: (IPartida | null | undefined)[]): IPartida[] {
    const partidas: IPartida[] = partidasToCheck.filter(isPresent);
    if (partidas.length > 0) {
      const partidaCollectionIdentifiers = partidaCollection.map(partidaItem => getPartidaIdentifier(partidaItem)!);
      const partidasToAdd = partidas.filter(partidaItem => {
        const partidaIdentifier = getPartidaIdentifier(partidaItem);
        if (partidaIdentifier == null || partidaCollectionIdentifiers.includes(partidaIdentifier)) {
          return false;
        }
        partidaCollectionIdentifiers.push(partidaIdentifier);
        return true;
      });
      return [...partidasToAdd, ...partidaCollection];
    }
    return partidaCollection;
  }
}
