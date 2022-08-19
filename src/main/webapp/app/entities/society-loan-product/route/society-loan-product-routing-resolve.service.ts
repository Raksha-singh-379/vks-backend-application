import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyLoanProduct } from '../society-loan-product.model';
import { SocietyLoanProductService } from '../service/society-loan-product.service';

@Injectable({ providedIn: 'root' })
export class SocietyLoanProductRoutingResolveService implements Resolve<ISocietyLoanProduct | null> {
  constructor(protected service: SocietyLoanProductService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyLoanProduct | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyLoanProduct: HttpResponse<ISocietyLoanProduct>) => {
          if (societyLoanProduct.body) {
            return of(societyLoanProduct.body);
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
