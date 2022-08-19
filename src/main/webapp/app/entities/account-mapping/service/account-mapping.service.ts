import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccountMapping, NewAccountMapping } from '../account-mapping.model';

export type PartialUpdateAccountMapping = Partial<IAccountMapping> & Pick<IAccountMapping, 'id'>;

type RestOf<T extends IAccountMapping | NewAccountMapping> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestAccountMapping = RestOf<IAccountMapping>;

export type NewRestAccountMapping = RestOf<NewAccountMapping>;

export type PartialUpdateRestAccountMapping = RestOf<PartialUpdateAccountMapping>;

export type EntityResponseType = HttpResponse<IAccountMapping>;
export type EntityArrayResponseType = HttpResponse<IAccountMapping[]>;

@Injectable({ providedIn: 'root' })
export class AccountMappingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/account-mappings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accountMapping: NewAccountMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountMapping);
    return this.http
      .post<RestAccountMapping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(accountMapping: IAccountMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountMapping);
    return this.http
      .put<RestAccountMapping>(`${this.resourceUrl}/${this.getAccountMappingIdentifier(accountMapping)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(accountMapping: PartialUpdateAccountMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountMapping);
    return this.http
      .patch<RestAccountMapping>(`${this.resourceUrl}/${this.getAccountMappingIdentifier(accountMapping)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAccountMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAccountMapping[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccountMappingIdentifier(accountMapping: Pick<IAccountMapping, 'id'>): number {
    return accountMapping.id;
  }

  compareAccountMapping(o1: Pick<IAccountMapping, 'id'> | null, o2: Pick<IAccountMapping, 'id'> | null): boolean {
    return o1 && o2 ? this.getAccountMappingIdentifier(o1) === this.getAccountMappingIdentifier(o2) : o1 === o2;
  }

  addAccountMappingToCollectionIfMissing<Type extends Pick<IAccountMapping, 'id'>>(
    accountMappingCollection: Type[],
    ...accountMappingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accountMappings: Type[] = accountMappingsToCheck.filter(isPresent);
    if (accountMappings.length > 0) {
      const accountMappingCollectionIdentifiers = accountMappingCollection.map(
        accountMappingItem => this.getAccountMappingIdentifier(accountMappingItem)!
      );
      const accountMappingsToAdd = accountMappings.filter(accountMappingItem => {
        const accountMappingIdentifier = this.getAccountMappingIdentifier(accountMappingItem);
        if (accountMappingCollectionIdentifiers.includes(accountMappingIdentifier)) {
          return false;
        }
        accountMappingCollectionIdentifiers.push(accountMappingIdentifier);
        return true;
      });
      return [...accountMappingsToAdd, ...accountMappingCollection];
    }
    return accountMappingCollection;
  }

  protected convertDateFromClient<T extends IAccountMapping | NewAccountMapping | PartialUpdateAccountMapping>(
    accountMapping: T
  ): RestOf<T> {
    return {
      ...accountMapping,
      lastModified: accountMapping.lastModified?.toJSON() ?? null,
      createdOn: accountMapping.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAccountMapping: RestAccountMapping): IAccountMapping {
    return {
      ...restAccountMapping,
      lastModified: restAccountMapping.lastModified ? dayjs(restAccountMapping.lastModified) : undefined,
      createdOn: restAccountMapping.createdOn ? dayjs(restAccountMapping.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAccountMapping>): HttpResponse<IAccountMapping> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAccountMapping[]>): HttpResponse<IAccountMapping[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
