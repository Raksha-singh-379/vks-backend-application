import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyCropRegistration } from '../society-crop-registration.model';
import { SocietyCropRegistrationService } from '../service/society-crop-registration.service';

@Injectable({ providedIn: 'root' })
export class SocietyCropRegistrationRoutingResolveService implements Resolve<ISocietyCropRegistration | null> {
  constructor(protected service: SocietyCropRegistrationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyCropRegistration | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyCropRegistration: HttpResponse<ISocietyCropRegistration>) => {
          if (societyCropRegistration.body) {
            return of(societyCropRegistration.body);
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
