import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyConfig } from '../society-config.model';
import { SocietyConfigService } from '../service/society-config.service';

@Injectable({ providedIn: 'root' })
export class SocietyConfigRoutingResolveService implements Resolve<ISocietyConfig | null> {
  constructor(protected service: SocietyConfigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyConfig | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyConfig: HttpResponse<ISocietyConfig>) => {
          if (societyConfig.body) {
            return of(societyConfig.body);
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
