import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import { LoanDisbursementDetailsService } from '../service/loan-disbursement-details.service';

@Injectable({ providedIn: 'root' })
export class LoanDisbursementDetailsRoutingResolveService implements Resolve<ILoanDisbursementDetails | null> {
  constructor(protected service: LoanDisbursementDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanDisbursementDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanDisbursementDetails: HttpResponse<ILoanDisbursementDetails>) => {
          if (loanDisbursementDetails.body) {
            return of(loanDisbursementDetails.body);
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
