import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyBanksDetails, NewSocietyBanksDetails } from '../society-banks-details.model';

export type PartialUpdateSocietyBanksDetails = Partial<ISocietyBanksDetails> & Pick<ISocietyBanksDetails, 'id'>;

type RestOf<T extends ISocietyBanksDetails | NewSocietyBanksDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyBanksDetails = RestOf<ISocietyBanksDetails>;

export type NewRestSocietyBanksDetails = RestOf<NewSocietyBanksDetails>;

export type PartialUpdateRestSocietyBanksDetails = RestOf<PartialUpdateSocietyBanksDetails>;

export type EntityResponseType = HttpResponse<ISocietyBanksDetails>;
export type EntityArrayResponseType = HttpResponse<ISocietyBanksDetails[]>;

@Injectable({ providedIn: 'root' })
export class SocietyBanksDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-banks-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyBanksDetails: NewSocietyBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyBanksDetails);
    return this.http
      .post<RestSocietyBanksDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyBanksDetails: ISocietyBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyBanksDetails);
    return this.http
      .put<RestSocietyBanksDetails>(`${this.resourceUrl}/${this.getSocietyBanksDetailsIdentifier(societyBanksDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyBanksDetails: PartialUpdateSocietyBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyBanksDetails);
    return this.http
      .patch<RestSocietyBanksDetails>(`${this.resourceUrl}/${this.getSocietyBanksDetailsIdentifier(societyBanksDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyBanksDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyBanksDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyBanksDetailsIdentifier(societyBanksDetails: Pick<ISocietyBanksDetails, 'id'>): number {
    return societyBanksDetails.id;
  }

  compareSocietyBanksDetails(o1: Pick<ISocietyBanksDetails, 'id'> | null, o2: Pick<ISocietyBanksDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyBanksDetailsIdentifier(o1) === this.getSocietyBanksDetailsIdentifier(o2) : o1 === o2;
  }

  addSocietyBanksDetailsToCollectionIfMissing<Type extends Pick<ISocietyBanksDetails, 'id'>>(
    societyBanksDetailsCollection: Type[],
    ...societyBanksDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyBanksDetails: Type[] = societyBanksDetailsToCheck.filter(isPresent);
    if (societyBanksDetails.length > 0) {
      const societyBanksDetailsCollectionIdentifiers = societyBanksDetailsCollection.map(
        societyBanksDetailsItem => this.getSocietyBanksDetailsIdentifier(societyBanksDetailsItem)!
      );
      const societyBanksDetailsToAdd = societyBanksDetails.filter(societyBanksDetailsItem => {
        const societyBanksDetailsIdentifier = this.getSocietyBanksDetailsIdentifier(societyBanksDetailsItem);
        if (societyBanksDetailsCollectionIdentifiers.includes(societyBanksDetailsIdentifier)) {
          return false;
        }
        societyBanksDetailsCollectionIdentifiers.push(societyBanksDetailsIdentifier);
        return true;
      });
      return [...societyBanksDetailsToAdd, ...societyBanksDetailsCollection];
    }
    return societyBanksDetailsCollection;
  }

  protected convertDateFromClient<T extends ISocietyBanksDetails | NewSocietyBanksDetails | PartialUpdateSocietyBanksDetails>(
    societyBanksDetails: T
  ): RestOf<T> {
    return {
      ...societyBanksDetails,
      lastModified: societyBanksDetails.lastModified?.toJSON() ?? null,
      createdOn: societyBanksDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyBanksDetails: RestSocietyBanksDetails): ISocietyBanksDetails {
    return {
      ...restSocietyBanksDetails,
      lastModified: restSocietyBanksDetails.lastModified ? dayjs(restSocietyBanksDetails.lastModified) : undefined,
      createdOn: restSocietyBanksDetails.createdOn ? dayjs(restSocietyBanksDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyBanksDetails>): HttpResponse<ISocietyBanksDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyBanksDetails[]>): HttpResponse<ISocietyBanksDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
