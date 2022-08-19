import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankDhoranDetails } from '../bank-dhoran-details.model';
import { BankDhoranDetailsService } from '../service/bank-dhoran-details.service';

@Injectable({ providedIn: 'root' })
export class BankDhoranDetailsRoutingResolveService implements Resolve<IBankDhoranDetails | null> {
  constructor(protected service: BankDhoranDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankDhoranDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankDhoranDetails: HttpResponse<IBankDhoranDetails>) => {
          if (bankDhoranDetails.body) {
            return of(bankDhoranDetails.body);
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
