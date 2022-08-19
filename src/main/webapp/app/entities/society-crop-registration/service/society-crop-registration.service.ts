import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyCropRegistration, NewSocietyCropRegistration } from '../society-crop-registration.model';

export type PartialUpdateSocietyCropRegistration = Partial<ISocietyCropRegistration> & Pick<ISocietyCropRegistration, 'id'>;

type RestOf<T extends ISocietyCropRegistration | NewSocietyCropRegistration> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyCropRegistration = RestOf<ISocietyCropRegistration>;

export type NewRestSocietyCropRegistration = RestOf<NewSocietyCropRegistration>;

export type PartialUpdateRestSocietyCropRegistration = RestOf<PartialUpdateSocietyCropRegistration>;

export type EntityResponseType = HttpResponse<ISocietyCropRegistration>;
export type EntityArrayResponseType = HttpResponse<ISocietyCropRegistration[]>;

@Injectable({ providedIn: 'root' })
export class SocietyCropRegistrationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-crop-registrations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyCropRegistration: NewSocietyCropRegistration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyCropRegistration);
    return this.http
      .post<RestSocietyCropRegistration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyCropRegistration: ISocietyCropRegistration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyCropRegistration);
    return this.http
      .put<RestSocietyCropRegistration>(`${this.resourceUrl}/${this.getSocietyCropRegistrationIdentifier(societyCropRegistration)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyCropRegistration: PartialUpdateSocietyCropRegistration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyCropRegistration);
    return this.http
      .patch<RestSocietyCropRegistration>(
        `${this.resourceUrl}/${this.getSocietyCropRegistrationIdentifier(societyCropRegistration)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyCropRegistration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyCropRegistration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyCropRegistrationIdentifier(societyCropRegistration: Pick<ISocietyCropRegistration, 'id'>): number {
    return societyCropRegistration.id;
  }

  compareSocietyCropRegistration(
    o1: Pick<ISocietyCropRegistration, 'id'> | null,
    o2: Pick<ISocietyCropRegistration, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getSocietyCropRegistrationIdentifier(o1) === this.getSocietyCropRegistrationIdentifier(o2) : o1 === o2;
  }

  addSocietyCropRegistrationToCollectionIfMissing<Type extends Pick<ISocietyCropRegistration, 'id'>>(
    societyCropRegistrationCollection: Type[],
    ...societyCropRegistrationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyCropRegistrations: Type[] = societyCropRegistrationsToCheck.filter(isPresent);
    if (societyCropRegistrations.length > 0) {
      const societyCropRegistrationCollectionIdentifiers = societyCropRegistrationCollection.map(
        societyCropRegistrationItem => this.getSocietyCropRegistrationIdentifier(societyCropRegistrationItem)!
      );
      const societyCropRegistrationsToAdd = societyCropRegistrations.filter(societyCropRegistrationItem => {
        const societyCropRegistrationIdentifier = this.getSocietyCropRegistrationIdentifier(societyCropRegistrationItem);
        if (societyCropRegistrationCollectionIdentifiers.includes(societyCropRegistrationIdentifier)) {
          return false;
        }
        societyCropRegistrationCollectionIdentifiers.push(societyCropRegistrationIdentifier);
        return true;
      });
      return [...societyCropRegistrationsToAdd, ...societyCropRegistrationCollection];
    }
    return societyCropRegistrationCollection;
  }

  protected convertDateFromClient<T extends ISocietyCropRegistration | NewSocietyCropRegistration | PartialUpdateSocietyCropRegistration>(
    societyCropRegistration: T
  ): RestOf<T> {
    return {
      ...societyCropRegistration,
      lastModified: societyCropRegistration.lastModified?.toJSON() ?? null,
      createdOn: societyCropRegistration.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyCropRegistration: RestSocietyCropRegistration): ISocietyCropRegistration {
    return {
      ...restSocietyCropRegistration,
      lastModified: restSocietyCropRegistration.lastModified ? dayjs(restSocietyCropRegistration.lastModified) : undefined,
      createdOn: restSocietyCropRegistration.createdOn ? dayjs(restSocietyCropRegistration.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyCropRegistration>): HttpResponse<ISocietyCropRegistration> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyCropRegistration[]>): HttpResponse<ISocietyCropRegistration[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
