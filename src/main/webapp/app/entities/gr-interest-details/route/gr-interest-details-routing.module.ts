import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GRInterestDetailsComponent } from '../list/gr-interest-details.component';
import { GRInterestDetailsDetailComponent } from '../detail/gr-interest-details-detail.component';
import { GRInterestDetailsUpdateComponent } from '../update/gr-interest-details-update.component';
import { GRInterestDetailsRoutingResolveService } from './gr-interest-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const gRInterestDetailsRoute: Routes = [
  {
    path: '',
    component: GRInterestDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GRInterestDetailsDetailComponent,
    resolve: {
      gRInterestDetails: GRInterestDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GRInterestDetailsUpdateComponent,
    resolve: {
      gRInterestDetails: GRInterestDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GRInterestDetailsUpdateComponent,
    resolve: {
      gRInterestDetails: GRInterestDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gRInterestDetailsRoute)],
  exports: [RouterModule],
})
export class GRInterestDetailsRoutingModule {}
