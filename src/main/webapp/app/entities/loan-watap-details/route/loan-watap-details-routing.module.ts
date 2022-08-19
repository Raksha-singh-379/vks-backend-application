import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanWatapDetailsComponent } from '../list/loan-watap-details.component';
import { LoanWatapDetailsDetailComponent } from '../detail/loan-watap-details-detail.component';
import { LoanWatapDetailsUpdateComponent } from '../update/loan-watap-details-update.component';
import { LoanWatapDetailsRoutingResolveService } from './loan-watap-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanWatapDetailsRoute: Routes = [
  {
    path: '',
    component: LoanWatapDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanWatapDetailsDetailComponent,
    resolve: {
      loanWatapDetails: LoanWatapDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanWatapDetailsUpdateComponent,
    resolve: {
      loanWatapDetails: LoanWatapDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanWatapDetailsUpdateComponent,
    resolve: {
      loanWatapDetails: LoanWatapDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanWatapDetailsRoute)],
  exports: [RouterModule],
})
export class LoanWatapDetailsRoutingModule {}
