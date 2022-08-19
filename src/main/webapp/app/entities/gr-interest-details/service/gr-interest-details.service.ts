import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGRInterestDetails, NewGRInterestDetails } from '../gr-interest-details.model';

export type PartialUpdateGRInterestDetails = Partial<IGRInterestDetails> & Pick<IGRInterestDetails, 'id'>;

type RestOf<T extends IGRInterestDetails | NewGRInterestDetails> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestGRInterestDetails = RestOf<IGRInterestDetails>;

export type NewRestGRInterestDetails = RestOf<NewGRInterestDetails>;

export type PartialUpdateRestGRInterestDetails = RestOf<PartialUpdateGRInterestDetails>;

export type EntityResponseType = HttpResponse<IGRInterestDetails>;
export type EntityArrayResponseType = HttpResponse<IGRInterestDetails[]>;

@Injectable({ providedIn: 'root' })
export class GRInterestDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gr-interest-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(gRInterestDetails: NewGRInterestDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gRInterestDetails);
    return this.http
      .post<RestGRInterestDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(gRInterestDetails: IGRInterestDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gRInterestDetails);
    return this.http
      .put<RestGRInterestDetails>(`${this.resourceUrl}/${this.getGRInterestDetailsIdentifier(gRInterestDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(gRInterestDetails: PartialUpdateGRInterestDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gRInterestDetails);
    return this.http
      .patch<RestGRInterestDetails>(`${this.resourceUrl}/${this.getGRInterestDetailsIdentifier(gRInterestDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGRInterestDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGRInterestDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGRInterestDetailsIdentifier(gRInterestDetails: Pick<IGRInterestDetails, 'id'>): number {
    return gRInterestDetails.id;
  }

  compareGRInterestDetails(o1: Pick<IGRInterestDetails, 'id'> | null, o2: Pick<IGRInterestDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getGRInterestDetailsIdentifier(o1) === this.getGRInterestDetailsIdentifier(o2) : o1 === o2;
  }

  addGRInterestDetailsToCollectionIfMissing<Type extends Pick<IGRInterestDetails, 'id'>>(
    gRInterestDetailsCollection: Type[],
    ...gRInterestDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const gRInterestDetails: Type[] = gRInterestDetailsToCheck.filter(isPresent);
    if (gRInterestDetails.length > 0) {
      const gRInterestDetailsCollectionIdentifiers = gRInterestDetailsCollection.map(
        gRInterestDetailsItem => this.getGRInterestDetailsIdentifier(gRInterestDetailsItem)!
      );
      const gRInterestDetailsToAdd = gRInterestDetails.filter(gRInterestDetailsItem => {
        const gRInterestDetailsIdentifier = this.getGRInterestDetailsIdentifier(gRInterestDetailsItem);
        if (gRInterestDetailsCollectionIdentifiers.includes(gRInterestDetailsIdentifier)) {
          return false;
        }
        gRInterestDetailsCollectionIdentifiers.push(gRInterestDetailsIdentifier);
        return true;
      });
      return [...gRInterestDetailsToAdd, ...gRInterestDetailsCollection];
    }
    return gRInterestDetailsCollection;
  }

  protected convertDateFromClient<T extends IGRInterestDetails | NewGRInterestDetails | PartialUpdateGRInterestDetails>(
    gRInterestDetails: T
  ): RestOf<T> {
    return {
      ...gRInterestDetails,
      startDate: gRInterestDetails.startDate?.toJSON() ?? null,
      endDate: gRInterestDetails.endDate?.toJSON() ?? null,
      lastModified: gRInterestDetails.lastModified?.toJSON() ?? null,
      createdOn: gRInterestDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGRInterestDetails: RestGRInterestDetails): IGRInterestDetails {
    return {
      ...restGRInterestDetails,
      startDate: restGRInterestDetails.startDate ? dayjs(restGRInterestDetails.startDate) : undefined,
      endDate: restGRInterestDetails.endDate ? dayjs(restGRInterestDetails.endDate) : undefined,
      lastModified: restGRInterestDetails.lastModified ? dayjs(restGRInterestDetails.lastModified) : undefined,
      createdOn: restGRInterestDetails.createdOn ? dayjs(restGRInterestDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGRInterestDetails>): HttpResponse<IGRInterestDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGRInterestDetails[]>): HttpResponse<IGRInterestDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
