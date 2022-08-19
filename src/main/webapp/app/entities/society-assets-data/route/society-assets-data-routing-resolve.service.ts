import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyAssetsData } from '../society-assets-data.model';
import { SocietyAssetsDataService } from '../service/society-assets-data.service';

@Injectable({ providedIn: 'root' })
export class SocietyAssetsDataRoutingResolveService implements Resolve<ISocietyAssetsData | null> {
  constructor(protected service: SocietyAssetsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyAssetsData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyAssetsData: HttpResponse<ISocietyAssetsData>) => {
          if (societyAssetsData.body) {
            return of(societyAssetsData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
