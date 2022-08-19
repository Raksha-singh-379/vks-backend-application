import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankDhoranDetails, NewBankDhoranDetails } from '../bank-dhoran-details.model';

export type PartialUpdateBankDhoranDetails = Partial<IBankDhoranDetails> & Pick<IBankDhoranDetails, 'id'>;

type RestOf<T extends IBankDhoranDetails | NewBankDhoranDetails> = Omit<T, 'startDate' | 'endDate' | 'lastModified'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
};

export type RestBankDhoranDetails = RestOf<IBankDhoranDetails>;

export type NewRestBankDhoranDetails = RestOf<NewBankDhoranDetails>;

export type PartialUpdateRestBankDhoranDetails = RestOf<PartialUpdateBankDhoranDetails>;

export type EntityResponseType = HttpResponse<IBankDhoranDetails>;
export type EntityArrayResponseType = HttpResponse<IBankDhoranDetails[]>;

@Injectable({ providedIn: 'root' })
export class BankDhoranDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-dhoran-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankDhoranDetails: NewBankDhoranDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankDhoranDetails);
    return this.http
      .post<RestBankDhoranDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(bankDhoranDetails: IBankDhoranDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankDhoranDetails);
    return this.http
      .put<RestBankDhoranDetails>(`${this.resourceUrl}/${this.getBankDhoranDetailsIdentifier(bankDhoranDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(bankDhoranDetails: PartialUpdateBankDhoranDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankDhoranDetails);
    return this.http
      .patch<RestBankDhoranDetails>(`${this.resourceUrl}/${this.getBankDhoranDetailsIdentifier(bankDhoranDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBankDhoranDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBankDhoranDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBankDhoranDetailsIdentifier(bankDhoranDetails: Pick<IBankDhoranDetails, 'id'>): number {
    return bankDhoranDetails.id;
  }

  compareBankDhoranDetails(o1: Pick<IBankDhoranDetails, 'id'> | null, o2: Pick<IBankDhoranDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getBankDhoranDetailsIdentifier(o1) === this.getBankDhoranDetailsIdentifier(o2) : o1 === o2;
  }

  addBankDhoranDetailsToCollectionIfMissing<Type extends Pick<IBankDhoranDetails, 'id'>>(
    bankDhoranDetailsCollection: Type[],
    ...bankDhoranDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bankDhoranDetails: Type[] = bankDhoranDetailsToCheck.filter(isPresent);
    if (bankDhoranDetails.length > 0) {
      const bankDhoranDetailsCollectionIdentifiers = bankDhoranDetailsCollection.map(
        bankDhoranDetailsItem => this.getBankDhoranDetailsIdentifier(bankDhoranDetailsItem)!
      );
      const bankDhoranDetailsToAdd = bankDhoranDetails.filter(bankDhoranDetailsItem => {
        const bankDhoranDetailsIdentifier = this.getBankDhoranDetailsIdentifier(bankDhoranDetailsItem);
        if (bankDhoranDetailsCollectionIdentifiers.includes(bankDhoranDetailsIdentifier)) {
          return false;
        }
        bankDhoranDetailsCollectionIdentifiers.push(bankDhoranDetailsIdentifier);
        return true;
      });
      return [...bankDhoranDetailsToAdd, ...bankDhoranDetailsCollection];
    }
    return bankDhoranDetailsCollection;
  }

  protected convertDateFromClient<T extends IBankDhoranDetails | NewBankDhoranDetails | PartialUpdateBankDhoranDetails>(
    bankDhoranDetails: T
  ): RestOf<T> {
    return {
      ...bankDhoranDetails,
      startDate: bankDhoranDetails.startDate?.toJSON() ?? null,
      endDate: bankDhoranDetails.endDate?.toJSON() ?? null,
      lastModified: bankDhoranDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restBankDhoranDetails: RestBankDhoranDetails): IBankDhoranDetails {
    return {
      ...restBankDhoranDetails,
      startDate: restBankDhoranDetails.startDate ? dayjs(restBankDhoranDetails.startDate) : undefined,
      endDate: restBankDhoranDetails.endDate ? dayjs(restBankDhoranDetails.endDate) : undefined,
      lastModified: restBankDhoranDetails.lastModified ? dayjs(restBankDhoranDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBankDhoranDetails>): HttpResponse<IBankDhoranDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBankDhoranDetails[]>): HttpResponse<IBankDhoranDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
