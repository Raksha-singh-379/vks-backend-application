import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanWatapDetails } from '../loan-watap-details.model';
import { LoanWatapDetailsService } from '../service/loan-watap-details.service';

@Injectable({ providedIn: 'root' })
export class LoanWatapDetailsRoutingResolveService implements Resolve<ILoanWatapDetails | null> {
  constructor(protected service: LoanWatapDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanWatapDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanWatapDetails: HttpResponse<ILoanWatapDetails>) => {
          if (loanWatapDetails.body) {
            return of(loanWatapDetails.body);
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
