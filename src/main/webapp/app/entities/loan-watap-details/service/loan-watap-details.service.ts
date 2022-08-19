import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanWatapDetails, NewLoanWatapDetails } from '../loan-watap-details.model';

export type PartialUpdateLoanWatapDetails = Partial<ILoanWatapDetails> & Pick<ILoanWatapDetails, 'id'>;

type RestOf<T extends ILoanWatapDetails | NewLoanWatapDetails> = Omit<T, 'loanWatapDate' | 'lastModified'> & {
  loanWatapDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanWatapDetails = RestOf<ILoanWatapDetails>;

export type NewRestLoanWatapDetails = RestOf<NewLoanWatapDetails>;

export type PartialUpdateRestLoanWatapDetails = RestOf<PartialUpdateLoanWatapDetails>;

export type EntityResponseType = HttpResponse<ILoanWatapDetails>;
export type EntityArrayResponseType = HttpResponse<ILoanWatapDetails[]>;

@Injectable({ providedIn: 'root' })
export class LoanWatapDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-watap-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanWatapDetails: NewLoanWatapDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanWatapDetails);
    return this.http
      .post<RestLoanWatapDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanWatapDetails: ILoanWatapDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanWatapDetails);
    return this.http
      .put<RestLoanWatapDetails>(`${this.resourceUrl}/${this.getLoanWatapDetailsIdentifier(loanWatapDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanWatapDetails: PartialUpdateLoanWatapDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanWatapDetails);
    return this.http
      .patch<RestLoanWatapDetails>(`${this.resourceUrl}/${this.getLoanWatapDetailsIdentifier(loanWatapDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanWatapDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanWatapDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanWatapDetailsIdentifier(loanWatapDetails: Pick<ILoanWatapDetails, 'id'>): number {
    return loanWatapDetails.id;
  }

  compareLoanWatapDetails(o1: Pick<ILoanWatapDetails, 'id'> | null, o2: Pick<ILoanWatapDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanWatapDetailsIdentifier(o1) === this.getLoanWatapDetailsIdentifier(o2) : o1 === o2;
  }

  addLoanWatapDetailsToCollectionIfMissing<Type extends Pick<ILoanWatapDetails, 'id'>>(
    loanWatapDetailsCollection: Type[],
    ...loanWatapDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanWatapDetails: Type[] = loanWatapDetailsToCheck.filter(isPresent);
    if (loanWatapDetails.length > 0) {
      const loanWatapDetailsCollectionIdentifiers = loanWatapDetailsCollection.map(
        loanWatapDetailsItem => this.getLoanWatapDetailsIdentifier(loanWatapDetailsItem)!
      );
      const loanWatapDetailsToAdd = loanWatapDetails.filter(loanWatapDetailsItem => {
        const loanWatapDetailsIdentifier = this.getLoanWatapDetailsIdentifier(loanWatapDetailsItem);
        if (loanWatapDetailsCollectionIdentifiers.includes(loanWatapDetailsIdentifier)) {
          return false;
        }
        loanWatapDetailsCollectionIdentifiers.push(loanWatapDetailsIdentifier);
        return true;
      });
      return [...loanWatapDetailsToAdd, ...loanWatapDetailsCollection];
    }
    return loanWatapDetailsCollection;
  }

  protected convertDateFromClient<T extends ILoanWatapDetails | NewLoanWatapDetails | PartialUpdateLoanWatapDetails>(
    loanWatapDetails: T
  ): RestOf<T> {
    return {
      ...loanWatapDetails,
      loanWatapDate: loanWatapDetails.loanWatapDate?.toJSON() ?? null,
      lastModified: loanWatapDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanWatapDetails: RestLoanWatapDetails): ILoanWatapDetails {
    return {
      ...restLoanWatapDetails,
      loanWatapDate: restLoanWatapDetails.loanWatapDate ? dayjs(restLoanWatapDetails.loanWatapDate) : undefined,
      lastModified: restLoanWatapDetails.lastModified ? dayjs(restLoanWatapDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanWatapDetails>): HttpResponse<ILoanWatapDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanWatapDetails[]>): HttpResponse<ILoanWatapDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
