import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocietyConfig, NewSocietyConfig } from '../society-config.model';

export type PartialUpdateSocietyConfig = Partial<ISocietyConfig> & Pick<ISocietyConfig, 'id'>;

type RestOf<T extends ISocietyConfig | NewSocietyConfig> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSocietyConfig = RestOf<ISocietyConfig>;

export type NewRestSocietyConfig = RestOf<NewSocietyConfig>;

export type PartialUpdateRestSocietyConfig = RestOf<PartialUpdateSocietyConfig>;

export type EntityResponseType = HttpResponse<ISocietyConfig>;
export type EntityArrayResponseType = HttpResponse<ISocietyConfig[]>;

@Injectable({ providedIn: 'root' })
export class SocietyConfigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/society-configs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societyConfig: NewSocietyConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyConfig);
    return this.http
      .post<RestSocietyConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(societyConfig: ISocietyConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyConfig);
    return this.http
      .put<RestSocietyConfig>(`${this.resourceUrl}/${this.getSocietyConfigIdentifier(societyConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(societyConfig: PartialUpdateSocietyConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(societyConfig);
    return this.http
      .patch<RestSocietyConfig>(`${this.resourceUrl}/${this.getSocietyConfigIdentifier(societyConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSocietyConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSocietyConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocietyConfigIdentifier(societyConfig: Pick<ISocietyConfig, 'id'>): number {
    return societyConfig.id;
  }

  compareSocietyConfig(o1: Pick<ISocietyConfig, 'id'> | null, o2: Pick<ISocietyConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocietyConfigIdentifier(o1) === this.getSocietyConfigIdentifier(o2) : o1 === o2;
  }

  addSocietyConfigToCollectionIfMissing<Type extends Pick<ISocietyConfig, 'id'>>(
    societyConfigCollection: Type[],
    ...societyConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societyConfigs: Type[] = societyConfigsToCheck.filter(isPresent);
    if (societyConfigs.length > 0) {
      const societyConfigCollectionIdentifiers = societyConfigCollection.map(
        societyConfigItem => this.getSocietyConfigIdentifier(societyConfigItem)!
      );
      const societyConfigsToAdd = societyConfigs.filter(societyConfigItem => {
        const societyConfigIdentifier = this.getSocietyConfigIdentifier(societyConfigItem);
        if (societyConfigCollectionIdentifiers.includes(societyConfigIdentifier)) {
          return false;
        }
        societyConfigCollectionIdentifiers.push(societyConfigIdentifier);
        return true;
      });
      return [...societyConfigsToAdd, ...societyConfigCollection];
    }
    return societyConfigCollection;
  }

  protected convertDateFromClient<T extends ISocietyConfig | NewSocietyConfig | PartialUpdateSocietyConfig>(societyConfig: T): RestOf<T> {
    return {
      ...societyConfig,
      lastModified: societyConfig.lastModified?.toJSON() ?? null,
      createdOn: societyConfig.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSocietyConfig: RestSocietyConfig): ISocietyConfig {
    return {
      ...restSocietyConfig,
      lastModified: restSocietyConfig.lastModified ? dayjs(restSocietyConfig.lastModified) : undefined,
      createdOn: restSocietyConfig.createdOn ? dayjs(restSocietyConfig.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSocietyConfig>): HttpResponse<ISocietyConfig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSocietyConfig[]>): HttpResponse<ISocietyConfig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
