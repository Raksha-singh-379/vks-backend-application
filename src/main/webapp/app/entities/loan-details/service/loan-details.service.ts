import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanDetails, NewLoanDetails } from '../loan-details.model';

export type PartialUpdateLoanDetails = Partial<ILoanDetails> & Pick<ILoanDetails, 'id'>;

type RestOf<T extends ILoanDetails | NewLoanDetails> = Omit<
  T,
  | 'loanStartDate'
  | 'loanEndDate'
  | 'loanPlannedClosureDate'
  | 'loanCloserDate'
  | 'loanEffectiveDate'
  | 'resolutionDate'
  | 'mortgageDate'
  | 'lastModified'
> & {
  loanStartDate?: string | null;
  loanEndDate?: string | null;
  loanPlannedClosureDate?: string | null;
  loanCloserDate?: string | null;
  loanEffectiveDate?: string | null;
  resolutionDate?: string | null;
  mortgageDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanDetails = RestOf<ILoanDetails>;

export type NewRestLoanDetails = RestOf<NewLoanDetails>;

export type PartialUpdateRestLoanDetails = RestOf<PartialUpdateLoanDetails>;

export type EntityResponseType = HttpResponse<ILoanDetails>;
export type EntityArrayResponseType = HttpResponse<ILoanDetails[]>;

@Injectable({ providedIn: 'root' })
export class LoanDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanDetails: NewLoanDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDetails);
    return this.http
      .post<RestLoanDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanDetails: ILoanDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDetails);
    return this.http
      .put<RestLoanDetails>(`${this.resourceUrl}/${this.getLoanDetailsIdentifier(loanDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanDetails: PartialUpdateLoanDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDetails);
    return this.http
      .patch<RestLoanDetails>(`${this.resourceUrl}/${this.getLoanDetailsIdentifier(loanDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanDetailsIdentifier(loanDetails: Pick<ILoanDetails, 'id'>): number {
    return loanDetails.id;
  }

  compareLoanDetails(o1: Pick<ILoanDetails, 'id'> | null, o2: Pick<ILoanDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanDetailsIdentifier(o1) === this.getLoanDetailsIdentifier(o2) : o1 === o2;
  }

  addLoanDetailsToCollectionIfMissing<Type extends Pick<ILoanDetails, 'id'>>(
    loanDetailsCollection: Type[],
    ...loanDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanDetails: Type[] = loanDetailsToCheck.filter(isPresent);
    if (loanDetails.length > 0) {
      const loanDetailsCollectionIdentifiers = loanDetailsCollection.map(
        loanDetailsItem => this.getLoanDetailsIdentifier(loanDetailsItem)!
      );
      const loanDetailsToAdd = loanDetails.filter(loanDetailsItem => {
        const loanDetailsIdentifier = this.getLoanDetailsIdentifier(loanDetailsItem);
        if (loanDetailsCollectionIdentifiers.includes(loanDetailsIdentifier)) {
          return false;
        }
        loanDetailsCollectionIdentifiers.push(loanDetailsIdentifier);
        return true;
      });
      return [...loanDetailsToAdd, ...loanDetailsCollection];
    }
    return loanDetailsCollection;
  }

  protected convertDateFromClient<T extends ILoanDetails | NewLoanDetails | PartialUpdateLoanDetails>(loanDetails: T): RestOf<T> {
    return {
      ...loanDetails,
      loanStartDate: loanDetails.loanStartDate?.toJSON() ?? null,
      loanEndDate: loanDetails.loanEndDate?.toJSON() ?? null,
      loanPlannedClosureDate: loanDetails.loanPlannedClosureDate?.toJSON() ?? null,
      loanCloserDate: loanDetails.loanCloserDate?.toJSON() ?? null,
      loanEffectiveDate: loanDetails.loanEffectiveDate?.toJSON() ?? null,
      resolutionDate: loanDetails.resolutionDate?.toJSON() ?? null,
      mortgageDate: loanDetails.mortgageDate?.toJSON() ?? null,
      lastModified: loanDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanDetails: RestLoanDetails): ILoanDetails {
    return {
      ...restLoanDetails,
      loanStartDate: restLoanDetails.loanStartDate ? dayjs(restLoanDetails.loanStartDate) : undefined,
      loanEndDate: restLoanDetails.loanEndDate ? dayjs(restLoanDetails.loanEndDate) : undefined,
      loanPlannedClosureDate: restLoanDetails.loanPlannedClosureDate ? dayjs(restLoanDetails.loanPlannedClosureDate) : undefined,
      loanCloserDate: restLoanDetails.loanCloserDate ? dayjs(restLoanDetails.loanCloserDate) : undefined,
      loanEffectiveDate: restLoanDetails.loanEffectiveDate ? dayjs(restLoanDetails.loanEffectiveDate) : undefined,
      resolutionDate: restLoanDetails.resolutionDate ? dayjs(restLoanDetails.resolutionDate) : undefined,
      mortgageDate: restLoanDetails.mortgageDate ? dayjs(restLoanDetails.mortgageDate) : undefined,
      lastModified: restLoanDetails.lastModified ? dayjs(restLoanDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanDetails>): HttpResponse<ILoanDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanDetails[]>): HttpResponse<ILoanDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
