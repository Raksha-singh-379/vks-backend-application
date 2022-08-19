import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExpenditureType } from '../expenditure-type.model';
import { ExpenditureTypeService } from '../service/expenditure-type.service';

@Injectable({ providedIn: 'root' })
export class ExpenditureTypeRoutingResolveService implements Resolve<IExpenditureType | null> {
  constructor(protected service: ExpenditureTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpenditureType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((expenditureType: HttpResponse<IExpenditureType>) => {
          if (expenditureType.body) {
            return of(expenditureType.body);
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
