import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { SocietyPrerequisiteService } from '../service/society-prerequisite.service';

@Injectable({ providedIn: 'root' })
export class SocietyPrerequisiteRoutingResolveService implements Resolve<ISocietyPrerequisite | null> {
  constructor(protected service: SocietyPrerequisiteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocietyPrerequisite | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((societyPrerequisite: HttpResponse<ISocietyPrerequisite>) => {
          if (societyPrerequisite.body) {
            return of(societyPrerequisite.body);
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
