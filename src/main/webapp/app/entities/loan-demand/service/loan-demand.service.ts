import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanDemand, NewLoanDemand } from '../loan-demand.model';

export type PartialUpdateLoanDemand = Partial<ILoanDemand> & Pick<ILoanDemand, 'id'>;

type RestOf<T extends ILoanDemand | NewLoanDemand> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestLoanDemand = RestOf<ILoanDemand>;

export type NewRestLoanDemand = RestOf<NewLoanDemand>;

export type PartialUpdateRestLoanDemand = RestOf<PartialUpdateLoanDemand>;

export type EntityResponseType = HttpResponse<ILoanDemand>;
export type EntityArrayResponseType = HttpResponse<ILoanDemand[]>;

@Injectable({ providedIn: 'root' })
export class LoanDemandService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-demands');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanDemand: NewLoanDemand): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemand);
    return this.http
      .post<RestLoanDemand>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanDemand: ILoanDemand): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemand);
    return this.http
      .put<RestLoanDemand>(`${this.resourceUrl}/${this.getLoanDemandIdentifier(loanDemand)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanDemand: PartialUpdateLoanDemand): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemand);
    return this.http
      .patch<RestLoanDemand>(`${this.resourceUrl}/${this.getLoanDemandIdentifier(loanDemand)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanDemand>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanDemand[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanDemandIdentifier(loanDemand: Pick<ILoanDemand, 'id'>): number {
    return loanDemand.id;
  }

  compareLoanDemand(o1: Pick<ILoanDemand, 'id'> | null, o2: Pick<ILoanDemand, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanDemandIdentifier(o1) === this.getLoanDemandIdentifier(o2) : o1 === o2;
  }

  addLoanDemandToCollectionIfMissing<Type extends Pick<ILoanDemand, 'id'>>(
    loanDemandCollection: Type[],
    ...loanDemandsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanDemands: Type[] = loanDemandsToCheck.filter(isPresent);
    if (loanDemands.length > 0) {
      const loanDemandCollectionIdentifiers = loanDemandCollection.map(loanDemandItem => this.getLoanDemandIdentifier(loanDemandItem)!);
      const loanDemandsToAdd = loanDemands.filter(loanDemandItem => {
        const loanDemandIdentifier = this.getLoanDemandIdentifier(loanDemandItem);
        if (loanDemandCollectionIdentifiers.includes(loanDemandIdentifier)) {
          return false;
        }
        loanDemandCollectionIdentifiers.push(loanDemandIdentifier);
        return true;
      });
      return [...loanDemandsToAdd, ...loanDemandCollection];
    }
    return loanDemandCollection;
  }

  protected convertDateFromClient<T extends ILoanDemand | NewLoanDemand | PartialUpdateLoanDemand>(loanDemand: T): RestOf<T> {
    return {
      ...loanDemand,
      lastModified: loanDemand.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanDemand: RestLoanDemand): ILoanDemand {
    return {
      ...restLoanDemand,
      lastModified: restLoanDemand.lastModified ? dayjs(restLoanDemand.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanDemand>): HttpResponse<ILoanDemand> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanDemand[]>): HttpResponse<ILoanDemand[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
