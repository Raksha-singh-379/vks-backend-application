import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyAssets, NewSocietyAssets } from '../society-assets.model';

export type PartialUpdateSocietyAssets = Partial<ISocietyAssets> & Pick<ISocietyAssets, 'id'>;

type RestOf<T extends ISocietyAssets | NewSocietyAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyAssets = RestOf<ISocietyAssets>;

export type NewRestSocietyAssets = RestOf<NewSocietyAssets>;

export type PartialUpdateRestSocietyAssets = RestOf<PartialUpdateSocietyAssets>;

export type EntityResponseType = HttpResponse<ISocietyAssets>;
export type EntityArrayResponseType = HttpResponse<ISocietyAssets[]>;

@Injectable({ providedIn: 'root' })
export class SocietyAssetsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-assets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyAssets: NewSocietyAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssets);
    return this.http
      .post<RestSocietyAssets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyAssets: ISocietyAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssets);
    return this.http
      .put<RestSocietyAssets>(`${this.resourceUrl}/${this.getSocietyAssetsIdentifier(societyAssets)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyAssets: PartialUpdateSocietyAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssets);
    return this.http
      .patch<RestSocietyAssets>(`${this.resourceUrl}/${this.getSocietyAssetsIdentifier(societyAssets)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyAssets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyAssets[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyAssetsIdentifier(societyAssets: Pick<ISocietyAssets, 'id'>): number {
    return societyAssets.id;
  }

  compareSocietyAssets(o1: Pick<ISocietyAssets, 'id'> | null, o2: Pick<ISocietyAssets, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyAssetsIdentifier(o1) === this.getSocietyAssetsIdentifier(o2) : o1 === o2;
  }

  addSocietyAssetsToCollectionIfMissing<Type extends Pick<ISocietyAssets, 'id'>>(
    societyAssetsCollection: Type[],
    ...societyAssetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyAssets: Type[] = societyAssetsToCheck.filter(isPresent);
    if (societyAssets.length > 0) {
      const societyAssetsCollectionIdentifiers = societyAssetsCollection.map(
        societyAssetsItem => this.getSocietyAssetsIdentifier(societyAssetsItem)!
      );
      const societyAssetsToAdd = societyAssets.filter(societyAssetsItem => {
        const societyAssetsIdentifier = this.getSocietyAssetsIdentifier(societyAssetsItem);
        if (societyAssetsCollectionIdentifiers.includes(societyAssetsIdentifier)) {
          return false;
        }
        societyAssetsCollectionIdentifiers.push(societyAssetsIdentifier);
        return true;
      });
      return [...societyAssetsToAdd, ...societyAssetsCollection];
    }
    return societyAssetsCollection;
  }

  protected convertDateFromClient<T extends ISocietyAssets | NewSocietyAssets | PartialUpdateSocietyAssets>(societyAssets: T): RestOf<T> {
    return {
      ...societyAssets,
      lastModified: societyAssets.lastModified?.toJSON() ?? null,
      createdOn: societyAssets.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyAssets: RestSocietyAssets): ISocietyAssets {
    return {
      ...restSocietyAssets,
      lastModified: restSocietyAssets.lastModified ? dayjs(restSocietyAssets.lastModified) : undefined,
      createdOn: restSocietyAssets.createdOn ? dayjs(restSocietyAssets.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyAssets>): HttpResponse<ISocietyAssets> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyAssets[]>): HttpResponse<ISocietyAssets[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
