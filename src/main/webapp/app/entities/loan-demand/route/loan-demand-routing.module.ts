import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanDemandComponent } from '../list/loan-demand.component';
import { LoanDemandDetailComponent } from '../detail/loan-demand-detail.component';
import { LoanDemandUpdateComponent } from '../update/loan-demand-update.component';
import { LoanDemandRoutingResolveService } from './loan-demand-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanDemandRoute: Routes = [
  {
    path: '',
    component: LoanDemandComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanDemandDetailComponent,
    resolve: {
      loanDemand: LoanDemandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanDemandUpdateComponent,
    resolve: {
      loanDemand: LoanDemandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanDemandUpdateComponent,
    resolve: {
      loanDemand: LoanDemandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanDemandRoute)],
  exports: [RouterModule],
})
export class LoanDemandRoutingModule {}
