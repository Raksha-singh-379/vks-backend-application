import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExpenditureType, NewExpenditureType } from '../expenditure-type.model';

export type PartialUpdateExpenditureType = Partial<IExpenditureType> & Pick<IExpenditureType, 'id'>;

type RestOf<T extends IExpenditureType | NewExpenditureType> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestExpenditureType = RestOf<IExpenditureType>;

export type NewRestExpenditureType = RestOf<NewExpenditureType>;

export type PartialUpdateRestExpenditureType = RestOf<PartialUpdateExpenditureType>;

export type EntityResponseType = HttpResponse<IExpenditureType>;
export type EntityArrayResponseType = HttpResponse<IExpenditureType[]>;

@Injectable({ providedIn: 'root' })
export class ExpenditureTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/expenditure-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(expenditureType: NewExpenditureType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expenditureType);
    return this.http
      .post<RestExpenditureType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(expenditureType: IExpenditureType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expenditureType);
    return this.http
      .put<RestExpenditureType>(`${this.resourceUrl}/${this.getExpenditureTypeIdentifier(expenditureType)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(expenditureType: PartialUpdateExpenditureType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expenditureType);
    return this.http
      .patch<RestExpenditureType>(`${this.resourceUrl}/${this.getExpenditureTypeIdentifier(expenditureType)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestExpenditureType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestExpenditureType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExpenditureTypeIdentifier(expenditureType: Pick<IExpenditureType, 'id'>): number {
    return expenditureType.id;
  }

  compareExpenditureType(o1: Pick<IExpenditureType, 'id'> | null, o2: Pick<IExpenditureType, 'id'> | null): boolean {
    return o1 && o2 ? this.getExpenditureTypeIdentifier(o1) === this.getExpenditureTypeIdentifier(o2) : o1 === o2;
  }

  addExpenditureTypeToCollectionIfMissing<Type extends Pick<IExpenditureType, 'id'>>(
    expenditureTypeCollection: Type[],
    ...expenditureTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const expenditureTypes: Type[] = expenditureTypesToCheck.filter(isPresent);
    if (expenditureTypes.length > 0) {
      const expenditureTypeCollectionIdentifiers = expenditureTypeCollection.map(
        expenditureTypeItem => this.getExpenditureTypeIdentifier(expenditureTypeItem)!
      );
      const expenditureTypesToAdd = expenditureTypes.filter(expenditureTypeItem => {
        const expenditureTypeIdentifier = this.getExpenditureTypeIdentifier(expenditureTypeItem);
        if (expenditureTypeCollectionIdentifiers.includes(expenditureTypeIdentifier)) {
          return false;
        }
        expenditureTypeCollectionIdentifiers.push(expenditureTypeIdentifier);
        return true;
      });
      return [...expenditureTypesToAdd, ...expenditureTypeCollection];
    }
    return expenditureTypeCollection;
  }

  protected convertDateFromClient<T extends IExpenditureType | NewExpenditureType | PartialUpdateExpenditureType>(
    expenditureType: T
  ): RestOf<T> {
    return {
      ...expenditureType,
      lastModified: expenditureType.lastModified?.toJSON() ?? null,
      createdOn: expenditureType.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restExpenditureType: RestExpenditureType): IExpenditureType {
    return {
      ...restExpenditureType,
      lastModified: restExpenditureType.lastModified ? dayjs(restExpenditureType.lastModified) : undefined,
      createdOn: restExpenditureType.createdOn ? dayjs(restExpenditureType.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestExpenditureType>): HttpResponse<IExpenditureType> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestExpenditureType[]>): HttpResponse<IExpenditureType[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
