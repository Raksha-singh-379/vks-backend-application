import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyBanksDetails } from '../society-banks-details.model';
import { SocietyBanksDetailsService } from '../service/society-banks-details.service';

@Injectable({ providedIn: 'root' })
export class SocietyBanksDetailsRoutingResolveService implements Resolve<ISocietyBanksDetails | null> {
  constructor(protected service: SocietyBanksDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyBanksDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyBanksDetails: HttpResponse<ISocietyBanksDetails>) => {
          if (societyBanksDetails.body) {
            return of(societyBanksDetails.body);
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
