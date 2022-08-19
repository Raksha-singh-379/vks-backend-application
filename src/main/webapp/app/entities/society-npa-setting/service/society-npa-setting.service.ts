import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyNpaSetting, NewSocietyNpaSetting } from '../society-npa-setting.model';

export type PartialUpdateSocietyNpaSetting = Partial<ISocietyNpaSetting> & Pick<ISocietyNpaSetting, 'id'>;

type RestOf<T extends ISocietyNpaSetting | NewSocietyNpaSetting> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyNpaSetting = RestOf<ISocietyNpaSetting>;

export type NewRestSocietyNpaSetting = RestOf<NewSocietyNpaSetting>;

export type PartialUpdateRestSocietyNpaSetting = RestOf<PartialUpdateSocietyNpaSetting>;

export type EntityResponseType = HttpResponse<ISocietyNpaSetting>;
export type EntityArrayResponseType = HttpResponse<ISocietyNpaSetting[]>;

@Injectable({ providedIn: 'root' })
export class SocietyNpaSettingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-npa-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyNpaSetting: NewSocietyNpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyNpaSetting);
    return this.http
      .post<RestSocietyNpaSetting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyNpaSetting: ISocietyNpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyNpaSetting);
    return this.http
      .put<RestSocietyNpaSetting>(`${this.resourceUrl}/${this.getSocietyNpaSettingIdentifier(societyNpaSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyNpaSetting: PartialUpdateSocietyNpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyNpaSetting);
    return this.http
      .patch<RestSocietyNpaSetting>(`${this.resourceUrl}/${this.getSocietyNpaSettingIdentifier(societyNpaSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyNpaSetting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyNpaSetting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyNpaSettingIdentifier(societyNpaSetting: Pick<ISocietyNpaSetting, 'id'>): number {
    return societyNpaSetting.id;
  }

  compareSocietyNpaSetting(o1: Pick<ISocietyNpaSetting, 'id'> | null, o2: Pick<ISocietyNpaSetting, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyNpaSettingIdentifier(o1) === this.getSocietyNpaSettingIdentifier(o2) : o1 === o2;
  }

  addSocietyNpaSettingToCollectionIfMissing<Type extends Pick<ISocietyNpaSetting, 'id'>>(
    societyNpaSettingCollection: Type[],
    ...societyNpaSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyNpaSettings: Type[] = societyNpaSettingsToCheck.filter(isPresent);
    if (societyNpaSettings.length > 0) {
      const societyNpaSettingCollectionIdentifiers = societyNpaSettingCollection.map(
        societyNpaSettingItem => this.getSocietyNpaSettingIdentifier(societyNpaSettingItem)!
      );
      const societyNpaSettingsToAdd = societyNpaSettings.filter(societyNpaSettingItem => {
        const societyNpaSettingIdentifier = this.getSocietyNpaSettingIdentifier(societyNpaSettingItem);
        if (societyNpaSettingCollectionIdentifiers.includes(societyNpaSettingIdentifier)) {
          return false;
        }
        societyNpaSettingCollectionIdentifiers.push(societyNpaSettingIdentifier);
        return true;
      });
      return [...societyNpaSettingsToAdd, ...societyNpaSettingCollection];
    }
    return societyNpaSettingCollection;
  }

  protected convertDateFromClient<T extends ISocietyNpaSetting | NewSocietyNpaSetting | PartialUpdateSocietyNpaSetting>(
    societyNpaSetting: T
  ): RestOf<T> {
    return {
      ...societyNpaSetting,
      lastModified: societyNpaSetting.lastModified?.toJSON() ?? null,
      createdOn: societyNpaSetting.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyNpaSetting: RestSocietyNpaSetting): ISocietyNpaSetting {
    return {
      ...restSocietyNpaSetting,
      lastModified: restSocietyNpaSetting.lastModified ? dayjs(restSocietyNpaSetting.lastModified) : undefined,
      createdOn: restSocietyNpaSetting.createdOn ? dayjs(restSocietyNpaSetting.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyNpaSetting>): HttpResponse<ISocietyNpaSetting> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyNpaSetting[]>): HttpResponse<ISocietyNpaSetting[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
