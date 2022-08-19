import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { SocietyNpaSettingService } from '../service/society-npa-setting.service';

@Injectable({ providedIn: 'root' })
export class SocietyNpaSettingRoutingResolveService implements Resolve<ISocietyNpaSetting | null> {
  constructor(protected service: SocietyNpaSettingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyNpaSetting | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyNpaSetting: HttpResponse<ISocietyNpaSetting>) => {
          if (societyNpaSetting.body) {
            return of(societyNpaSetting.body);
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
