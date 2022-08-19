import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyBanksDetailsComponent } from '../list/society-banks-details.component';
import { SocietyBanksDetailsDetailComponent } from '../detail/society-banks-details-detail.component';
import { SocietyBanksDetailsUpdateComponent } from '../update/society-banks-details-update.component';
import { SocietyBanksDetailsRoutingResolveService } from './society-banks-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyBanksDetailsRoute: Routes = [
  {
    path: '',
    component: SocietyBanksDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyBanksDetailsDetailComponent,
    resolve: {
      societyBanksDetails: SocietyBanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyBanksDetailsUpdateComponent,
    resolve: {
      societyBanksDetails: SocietyBanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyBanksDetailsUpdateComponent,
    resolve: {
      societyBanksDetails: SocietyBanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyBanksDetailsRoute)],
  exports: [RouterModule],
})
export class SocietyBanksDetailsRoutingModule {}
