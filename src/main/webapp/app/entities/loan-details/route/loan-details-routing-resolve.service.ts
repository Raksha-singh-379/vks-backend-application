import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanDetails } from '../loan-details.model';
import { LoanDetailsService } from '../service/loan-details.service';

@Injectable({ providedIn: 'root' })
export class LoanDetailsRoutingResolveService implements Resolve<ILoanDetails | null> {
  constructor(protected service: LoanDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanDetails: HttpResponse<ILoanDetails>) => {
          if (loanDetails.body) {
            return of(loanDetails.body);
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
