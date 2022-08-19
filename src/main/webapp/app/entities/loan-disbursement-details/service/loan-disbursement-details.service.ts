import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanDisbursementDetails, NewLoanDisbursementDetails } from '../loan-disbursement-details.model';

export type PartialUpdateLoanDisbursementDetails = Partial<ILoanDisbursementDetails> & Pick<ILoanDisbursementDetails, 'id'>;

type RestOf<T extends ILoanDisbursementDetails | NewLoanDisbursementDetails> = Omit<T, 'disbursementDate' | 'lastModified'> & {
  disbursementDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanDisbursementDetails = RestOf<ILoanDisbursementDetails>;

export type NewRestLoanDisbursementDetails = RestOf<NewLoanDisbursementDetails>;

export type PartialUpdateRestLoanDisbursementDetails = RestOf<PartialUpdateLoanDisbursementDetails>;

export type EntityResponseType = HttpResponse<ILoanDisbursementDetails>;
export type EntityArrayResponseType = HttpResponse<ILoanDisbursementDetails[]>;

@Injectable({ providedIn: 'root' })
export class LoanDisbursementDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-disbursement-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanDisbursementDetails: NewLoanDisbursementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursementDetails);
    return this.http
      .post<RestLoanDisbursementDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanDisbursementDetails: ILoanDisbursementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursementDetails);
    return this.http
      .put<RestLoanDisbursementDetails>(`${this.resourceUrl}/${this.getLoanDisbursementDetailsIdentifier(loanDisbursementDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanDisbursementDetails: PartialUpdateLoanDisbursementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursementDetails);
    return this.http
      .patch<RestLoanDisbursementDetails>(
        `${this.resourceUrl}/${this.getLoanDisbursementDetailsIdentifier(loanDisbursementDetails)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanDisbursementDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanDisbursementDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanDisbursementDetailsIdentifier(loanDisbursementDetails: Pick<ILoanDisbursementDetails, 'id'>): number {
    return loanDisbursementDetails.id;
  }

  compareLoanDisbursementDetails(
    o1: Pick<ILoanDisbursementDetails, 'id'> | null,
    o2: Pick<ILoanDisbursementDetails, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getLoanDisbursementDetailsIdentifier(o1) === this.getLoanDisbursementDetailsIdentifier(o2) : o1 === o2;
  }

  addLoanDisbursementDetailsToCollectionIfMissing<Type extends Pick<ILoanDisbursementDetails, 'id'>>(
    loanDisbursementDetailsCollection: Type[],
    ...loanDisbursementDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanDisbursementDetails: Type[] = loanDisbursementDetailsToCheck.filter(isPresent);
    if (loanDisbursementDetails.length > 0) {
      const loanDisbursementDetailsCollectionIdentifiers = loanDisbursementDetailsCollection.map(
        loanDisbursementDetailsItem => this.getLoanDisbursementDetailsIdentifier(loanDisbursementDetailsItem)!
      );
      const loanDisbursementDetailsToAdd = loanDisbursementDetails.filter(loanDisbursementDetailsItem => {
        const loanDisbursementDetailsIdentifier = this.getLoanDisbursementDetailsIdentifier(loanDisbursementDetailsItem);
        if (loanDisbursementDetailsCollectionIdentifiers.includes(loanDisbursementDetailsIdentifier)) {
          return false;
        }
        loanDisbursementDetailsCollectionIdentifiers.push(loanDisbursementDetailsIdentifier);
        return true;
      });
      return [...loanDisbursementDetailsToAdd, ...loanDisbursementDetailsCollection];
    }
    return loanDisbursementDetailsCollection;
  }

  protected convertDateFromClient<T extends ILoanDisbursementDetails | NewLoanDisbursementDetails | PartialUpdateLoanDisbursementDetails>(
    loanDisbursementDetails: T
  ): RestOf<T> {
    return {
      ...loanDisbursementDetails,
      disbursementDate: loanDisbursementDetails.disbursementDate?.toJSON() ?? null,
      lastModified: loanDisbursementDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanDisbursementDetails: RestLoanDisbursementDetails): ILoanDisbursementDetails {
    return {
      ...restLoanDisbursementDetails,
      disbursementDate: restLoanDisbursementDetails.disbursementDate ? dayjs(restLoanDisbursementDetails.disbursementDate) : undefined,
      lastModified: restLoanDisbursementDetails.lastModified ? dayjs(restLoanDisbursementDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanDisbursementDetails>): HttpResponse<ILoanDisbursementDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanDisbursementDetails[]>): HttpResponse<ILoanDisbursementDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
