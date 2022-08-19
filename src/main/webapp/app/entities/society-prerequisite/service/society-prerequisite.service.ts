import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyPrerequisite, NewSocietyPrerequisite } from '../society-prerequisite.model';

export type PartialUpdateSocietyPrerequisite = Partial<ISocietyPrerequisite> & Pick<ISocietyPrerequisite, 'id'>;

type RestOf<T extends ISocietyPrerequisite | NewSocietyPrerequisite> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyPrerequisite = RestOf<ISocietyPrerequisite>;

export type NewRestSocietyPrerequisite = RestOf<NewSocietyPrerequisite>;

export type PartialUpdateRestSocietyPrerequisite = RestOf<PartialUpdateSocietyPrerequisite>;

export type EntityResponseType = HttpResponse<ISocietyPrerequisite>;
export type EntityArrayResponseType = HttpResponse<ISocietyPrerequisite[]>;

@Injectable({ providedIn: 'root' })
export class SocietyPrerequisiteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-prerequisites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyPrerequisite: NewSocietyPrerequisite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyPrerequisite);
    return this.http
      .post<RestSocietyPrerequisite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyPrerequisite: ISocietyPrerequisite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyPrerequisite);
    return this.http
      .put<RestSocietyPrerequisite>(`${this.resourceUrl}/${this.getSocietyPrerequisiteIdentifier(societyPrerequisite)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyPrerequisite: PartialUpdateSocietyPrerequisite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyPrerequisite);
    return this.http
      .patch<RestSocietyPrerequisite>(`${this.resourceUrl}/${this.getSocietyPrerequisiteIdentifier(societyPrerequisite)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyPrerequisite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyPrerequisite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyPrerequisiteIdentifier(societyPrerequisite: Pick<ISocietyPrerequisite, 'id'>): number {
    return societyPrerequisite.id;
  }

  compareSocietyPrerequisite(o1: Pick<ISocietyPrerequisite, 'id'> | null, o2: Pick<ISocietyPrerequisite, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyPrerequisiteIdentifier(o1) === this.getSocietyPrerequisiteIdentifier(o2) : o1 === o2;
  }

  addSocietyPrerequisiteToCollectionIfMissing<Type extends Pick<ISocietyPrerequisite, 'id'>>(
    societyPrerequisiteCollection: Type[],
    ...societyPrerequisitesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyPrerequisites: Type[] = societyPrerequisitesToCheck.filter(isPresent);
    if (societyPrerequisites.length > 0) {
      const societyPrerequisiteCollectionIdentifiers = societyPrerequisiteCollection.map(
        societyPrerequisiteItem => this.getSocietyPrerequisiteIdentifier(societyPrerequisiteItem)!
      );
      const societyPrerequisitesToAdd = societyPrerequisites.filter(societyPrerequisiteItem => {
        const societyPrerequisiteIdentifier = this.getSocietyPrerequisiteIdentifier(societyPrerequisiteItem);
        if (societyPrerequisiteCollectionIdentifiers.includes(societyPrerequisiteIdentifier)) {
          return false;
        }
        societyPrerequisiteCollectionIdentifiers.push(societyPrerequisiteIdentifier);
        return true;
      });
      return [...societyPrerequisitesToAdd, ...societyPrerequisiteCollection];
    }
    return societyPrerequisiteCollection;
  }

  protected convertDateFromClient<T extends ISocietyPrerequisite | NewSocietyPrerequisite | PartialUpdateSocietyPrerequisite>(
    societyPrerequisite: T
  ): RestOf<T> {
    return {
      ...societyPrerequisite,
      lastModified: societyPrerequisite.lastModified?.toJSON() ?? null,
      createdOn: societyPrerequisite.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyPrerequisite: RestSocietyPrerequisite): ISocietyPrerequisite {
    return {
      ...restSocietyPrerequisite,
      lastModified: restSocietyPrerequisite.lastModified ? dayjs(restSocietyPrerequisite.lastModified) : undefined,
      createdOn: restSocietyPrerequisite.createdOn ? dayjs(restSocietyPrerequisite.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyPrerequisite>): HttpResponse<ISocietyPrerequisite> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyPrerequisite[]>): HttpResponse<ISocietyPrerequisite[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
