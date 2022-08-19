import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyAssets } from '../society-assets.model';
import { SocietyAssetsService } from '../service/society-assets.service';

@Injectable({ providedIn: 'root' })
export class SocietyAssetsRoutingResolveService implements Resolve<ISocietyAssets | null> {
  constructor(protected service: SocietyAssetsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyAssets | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyAssets: HttpResponse<ISocietyAssets>) => {
          if (societyAssets.body) {
            return of(societyAssets.body);
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
