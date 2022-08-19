import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyPrerequisiteComponent } from '../list/society-prerequisite.component';
import { SocietyPrerequisiteDetailComponent } from '../detail/society-prerequisite-detail.component';
import { SocietyPrerequisiteUpdateComponent } from '../update/society-prerequisite-update.component';
import { SocietyPrerequisiteRoutingResolveService } from './society-prerequisite-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyPrerequisiteRoute: Routes = [
  {
    path: '',
    component: SocietyPrerequisiteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyPrerequisiteDetailComponent,
    resolve: {
      societyPrerequisite: SocietyPrerequisiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyPrerequisiteUpdateComponent,
    resolve: {
      societyPrerequisite: SocietyPrerequisiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyPrerequisiteUpdateComponent,
    resolve: {
      societyPrerequisite: SocietyPrerequisiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyPrerequisiteRoute)],
  exports: [RouterModule],
})
export class SocietyPrerequisiteRoutingModule {}
