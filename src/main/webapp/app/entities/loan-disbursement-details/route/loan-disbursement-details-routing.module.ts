import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanDisbursementDetailsComponent } from '../list/loan-disbursement-details.component';
import { LoanDisbursementDetailsDetailComponent } from '../detail/loan-disbursement-details-detail.component';
import { LoanDisbursementDetailsUpdateComponent } from '../update/loan-disbursement-details-update.component';
import { LoanDisbursementDetailsRoutingResolveService } from './loan-disbursement-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanDisbursementDetailsRoute: Routes = [
  {
    path: '',
    component: LoanDisbursementDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanDisbursementDetailsDetailComponent,
    resolve: {
      loanDisbursementDetails: LoanDisbursementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanDisbursementDetailsUpdateComponent,
    resolve: {
      loanDisbursementDetails: LoanDisbursementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanDisbursementDetailsUpdateComponent,
    resolve: {
      loanDisbursementDetails: LoanDisbursementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanDisbursementDetailsRoute)],
  exports: [RouterModule],
})
export class LoanDisbursementDetailsRoutingModule {}
