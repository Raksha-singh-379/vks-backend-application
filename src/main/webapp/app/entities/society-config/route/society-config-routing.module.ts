import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyConfigComponent } from '../list/society-config.component';
import { SocietyConfigDetailComponent } from '../detail/society-config-detail.component';
import { SocietyConfigUpdateComponent } from '../update/society-config-update.component';
import { SocietyConfigRoutingResolveService } from './society-config-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyConfigRoute: Routes = [
  {
    path: '',
    component: SocietyConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyConfigDetailComponent,
    resolve: {
      societyConfig: SocietyConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyConfigUpdateComponent,
    resolve: {
      societyConfig: SocietyConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyConfigUpdateComponent,
    resolve: {
      societyConfig: SocietyConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyConfigRoute)],
  exports: [RouterModule],
})
export class SocietyConfigRoutingModule {}
