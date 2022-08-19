import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanDemand } from '../loan-demand.model';
import { LoanDemandService } from '../service/loan-demand.service';

@Injectable({ providedIn: 'root' })
export class LoanDemandRoutingResolveService implements Resolve<ILoanDemand | null> {
  constructor(protected service: LoanDemandService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanDemand | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanDemand: HttpResponse<ILoanDemand>) => {
          if (loanDemand.body) {
            return of(loanDemand.body);
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
