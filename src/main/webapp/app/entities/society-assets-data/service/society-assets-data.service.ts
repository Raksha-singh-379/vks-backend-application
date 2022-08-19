import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyAssetsData, NewSocietyAssetsData } from '../society-assets-data.model';

export type PartialUpdateSocietyAssetsData = Partial<ISocietyAssetsData> & Pick<ISocietyAssetsData, 'id'>;

type RestOf<T extends ISocietyAssetsData | NewSocietyAssetsData> = Omit<T, 'transactionDate' | 'lastModified' | 'createdOn'> & {
  transactionDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyAssetsData = RestOf<ISocietyAssetsData>;

export type NewRestSocietyAssetsData = RestOf<NewSocietyAssetsData>;

export type PartialUpdateRestSocietyAssetsData = RestOf<PartialUpdateSocietyAssetsData>;

export type EntityResponseType = HttpResponse<ISocietyAssetsData>;
export type EntityArrayResponseType = HttpResponse<ISocietyAssetsData[]>;

@Injectable({ providedIn: 'root' })
export class SocietyAssetsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-assets-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyAssetsData: NewSocietyAssetsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssetsData);
    return this.http
      .post<RestSocietyAssetsData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyAssetsData: ISocietyAssetsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssetsData);
    return this.http
      .put<RestSocietyAssetsData>(`${this.resourceUrl}/${this.getSocietyAssetsDataIdentifier(societyAssetsData)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyAssetsData: PartialUpdateSocietyAssetsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyAssetsData);
    return this.http
      .patch<RestSocietyAssetsData>(`${this.resourceUrl}/${this.getSocietyAssetsDataIdentifier(societyAssetsData)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyAssetsData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyAssetsData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyAssetsDataIdentifier(societyAssetsData: Pick<ISocietyAssetsData, 'id'>): number {
    return societyAssetsData.id;
  }

  compareSocietyAssetsData(o1: Pick<ISocietyAssetsData, 'id'> | null, o2: Pick<ISocietyAssetsData, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyAssetsDataIdentifier(o1) === this.getSocietyAssetsDataIdentifier(o2) : o1 === o2;
  }

  addSocietyAssetsDataToCollectionIfMissing<Type extends Pick<ISocietyAssetsData, 'id'>>(
    societyAssetsDataCollection: Type[],
    ...societyAssetsDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyAssetsData: Type[] = societyAssetsDataToCheck.filter(isPresent);
    if (societyAssetsData.length > 0) {
      const societyAssetsDataCollectionIdentifiers = societyAssetsDataCollection.map(
        societyAssetsDataItem => this.getSocietyAssetsDataIdentifier(societyAssetsDataItem)!
      );
      const societyAssetsDataToAdd = societyAssetsData.filter(societyAssetsDataItem => {
        const societyAssetsDataIdentifier = this.getSocietyAssetsDataIdentifier(societyAssetsDataItem);
        if (societyAssetsDataCollectionIdentifiers.includes(societyAssetsDataIdentifier)) {
          return false;
        }
        societyAssetsDataCollectionIdentifiers.push(societyAssetsDataIdentifier);
        return true;
      });
      return [...societyAssetsDataToAdd, ...societyAssetsDataCollection];
    }
    return societyAssetsDataCollection;
  }

  protected convertDateFromClient<T extends ISocietyAssetsData | NewSocietyAssetsData | PartialUpdateSocietyAssetsData>(
    societyAssetsData: T
  ): RestOf<T> {
    return {
      ...societyAssetsData,
      transactionDate: societyAssetsData.transactionDate?.toJSON() ?? null,
      lastModified: societyAssetsData.lastModified?.toJSON() ?? null,
      createdOn: societyAssetsData.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyAssetsData: RestSocietyAssetsData): ISocietyAssetsData {
    return {
      ...restSocietyAssetsData,
      transactionDate: restSocietyAssetsData.transactionDate ? dayjs(restSocietyAssetsData.transactionDate) : undefined,
      lastModified: restSocietyAssetsData.lastModified ? dayjs(restSocietyAssetsData.lastModified) : undefined,
      createdOn: restSocietyAssetsData.createdOn ? dayjs(restSocietyAssetsData.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyAssetsData>): HttpResponse<ISocietyAssetsData> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyAssetsData[]>): HttpResponse<ISocietyAssetsData[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
