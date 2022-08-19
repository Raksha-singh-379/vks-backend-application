import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanDetailsComponent } from '../list/loan-details.component';
import { LoanDetailsDetailComponent } from '../detail/loan-details-detail.component';
import { LoanDetailsUpdateComponent } from '../update/loan-details-update.component';
import { LoanDetailsRoutingResolveService } from './loan-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanDetailsRoute: Routes = [
  {
    path: '',
    component: LoanDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanDetailsDetailComponent,
    resolve: {
      loanDetails: LoanDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanDetailsUpdateComponent,
    resolve: {
      loanDetails: LoanDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanDetailsUpdateComponent,
    resolve: {
      loanDetails: LoanDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanDetailsRoute)],
  exports: [RouterModule],
})
export class LoanDetailsRoutingModule {}
