import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccountMapping } from '../account-mapping.model';
import { AccountMappingService } from '../service/account-mapping.service';

@Injectable({ providedIn: 'root' })
export class AccountMappingRoutingResolveService implements Resolve<IAccountMapping | null> {
  constructor(protected service: AccountMappingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountMapping | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accountMapping: HttpResponse<IAccountMapping>) => {
          if (accountMapping.body) {
            return of(accountMapping.body);
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
