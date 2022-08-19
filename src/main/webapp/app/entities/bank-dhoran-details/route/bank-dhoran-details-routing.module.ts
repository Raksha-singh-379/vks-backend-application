import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankDhoranDetailsComponent } from '../list/bank-dhoran-details.component';
import { BankDhoranDetailsDetailComponent } from '../detail/bank-dhoran-details-detail.component';
import { BankDhoranDetailsUpdateComponent } from '../update/bank-dhoran-details-update.component';
import { BankDhoranDetailsRoutingResolveService } from './bank-dhoran-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bankDhoranDetailsRoute: Routes = [
  {
    path: '',
    component: BankDhoranDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankDhoranDetailsDetailComponent,
    resolve: {
      bankDhoranDetails: BankDhoranDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankDhoranDetailsUpdateComponent,
    resolve: {
      bankDhoranDetails: BankDhoranDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankDhoranDetailsUpdateComponent,
    resolve: {
      bankDhoranDetails: BankDhoranDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankDhoranDetailsRoute)],
  exports: [RouterModule],
})
export class BankDhoranDetailsRoutingModule {}
