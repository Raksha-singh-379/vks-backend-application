import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGRInterestDetails } from '../gr-interest-details.model';
import { GRInterestDetailsService } from '../service/gr-interest-details.service';

@Injectable({ providedIn: 'root' })
export class GRInterestDetailsRoutingResolveService implements Resolve<IGRInterestDetails | null> {
  constructor(protected service: GRInterestDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGRInterestDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gRInterestDetails: HttpResponse<IGRInterestDetails>) => {
          if (gRInterestDetails.body) {
            return of(gRInterestDetails.body);
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
