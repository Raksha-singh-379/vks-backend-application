import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyLoanProduct, NewSocietyLoanProduct } from '../society-loan-product.model';

export type PartialUpdateSocietyLoanProduct = Partial<ISocietyLoanProduct> & Pick<ISocietyLoanProduct, 'id'>;

type RestOf<T extends ISocietyLoanProduct | NewSocietyLoanProduct> = Omit<
  T,
  'lastDateOfRepayment' | 'resolutionDate' | 'validFrom' | 'validTo' | 'createdOn' | 'lastModified'
> & {
  lastDateOfRepayment?: string | null;
  resolutionDate?: string | null;
  validFrom?: string | null;
  validTo?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
};

export type RestSocietyLoanProduct = RestOf<ISocietyLoanProduct>;

export type NewRestSocietyLoanProduct = RestOf<NewSocietyLoanProduct>;

export type PartialUpdateRestSocietyLoanProduct = RestOf<PartialUpdateSocietyLoanProduct>;

export type EntityResponseType = HttpResponse<ISocietyLoanProduct>;
export type EntityArrayResponseType = HttpResponse<ISocietyLoanProduct[]>;

@Injectable({ providedIn: 'root' })
export class SocietyLoanProductService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-loan-products');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyLoanProduct: NewSocietyLoanProduct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyLoanProduct);
    return this.http
      .post<RestSocietyLoanProduct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyLoanProduct: ISocietyLoanProduct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyLoanProduct);
    return this.http
      .put<RestSocietyLoanProduct>(`${this.resourceUrl}/${this.getSocietyLoanProductIdentifier(societyLoanProduct)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyLoanProduct: PartialUpdateSocietyLoanProduct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyLoanProduct);
    return this.http
      .patch<RestSocietyLoanProduct>(`${this.resourceUrl}/${this.getSocietyLoanProductIdentifier(societyLoanProduct)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyLoanProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyLoanProduct[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyLoanProductIdentifier(societyLoanProduct: Pick<ISocietyLoanProduct, 'id'>): number {
    return societyLoanProduct.id;
  }

  compareSocietyLoanProduct(o1: Pick<ISocietyLoanProduct, 'id'> | null, o2: Pick<ISocietyLoanProduct, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyLoanProductIdentifier(o1) === this.getSocietyLoanProductIdentifier(o2) : o1 === o2;
  }

  addSocietyLoanProductToCollectionIfMissing<Type extends Pick<ISocietyLoanProduct, 'id'>>(
    societyLoanProductCollection: Type[],
    ...societyLoanProductsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyLoanProducts: Type[] = societyLoanProductsToCheck.filter(isPresent);
    if (societyLoanProducts.length > 0) {
      const societyLoanProductCollectionIdentifiers = societyLoanProductCollection.map(
        societyLoanProductItem => this.getSocietyLoanProductIdentifier(societyLoanProductItem)!
      );
      const societyLoanProductsToAdd = societyLoanProducts.filter(societyLoanProductItem => {
        const societyLoanProductIdentifier = this.getSocietyLoanProductIdentifier(societyLoanProductItem);
        if (societyLoanProductCollectionIdentifiers.includes(societyLoanProductIdentifier)) {
          return false;
        }
        societyLoanProductCollectionIdentifiers.push(societyLoanProductIdentifier);
        return true;
      });
      return [...societyLoanProductsToAdd, ...societyLoanProductCollection];
    }
    return societyLoanProductCollection;
  }

  protected convertDateFromClient<T extends ISocietyLoanProduct | NewSocietyLoanProduct | PartialUpdateSocietyLoanProduct>(
    societyLoanProduct: T
  ): RestOf<T> {
    return {
      ...societyLoanProduct,
      lastDateOfRepayment: societyLoanProduct.lastDateOfRepayment?.toJSON() ?? null,
      resolutionDate: societyLoanProduct.resolutionDate?.toJSON() ?? null,
      validFrom: societyLoanProduct.validFrom?.toJSON() ?? null,
      validTo: societyLoanProduct.validTo?.toJSON() ?? null,
      createdOn: societyLoanProduct.createdOn?.toJSON() ?? null,
      lastModified: societyLoanProduct.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyLoanProduct: RestSocietyLoanProduct): ISocietyLoanProduct {
    return {
      ...restSocietyLoanProduct,
      lastDateOfRepayment: restSocietyLoanProduct.lastDateOfRepayment ? dayjs(restSocietyLoanProduct.lastDateOfRepayment) : undefined,
      resolutionDate: restSocietyLoanProduct.resolutionDate ? dayjs(restSocietyLoanProduct.resolutionDate) : undefined,
      validFrom: restSocietyLoanProduct.validFrom ? dayjs(restSocietyLoanProduct.validFrom) : undefined,
      validTo: restSocietyLoanProduct.validTo ? dayjs(restSocietyLoanProduct.validTo) : undefined,
      createdOn: restSocietyLoanProduct.createdOn ? dayjs(restSocietyLoanProduct.createdOn) : undefined,
      lastModified: restSocietyLoanProduct.lastModified ? dayjs(restSocietyLoanProduct.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyLoanProduct>): HttpResponse<ISocietyLoanProduct> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyLoanProduct[]>): HttpResponse<ISocietyLoanProduct[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
