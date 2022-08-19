import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExpenditureTypeComponent } from '../list/expenditure-type.component';
import { ExpenditureTypeDetailComponent } from '../detail/expenditure-type-detail.component';
import { ExpenditureTypeUpdateComponent } from '../update/expenditure-type-update.component';
import { ExpenditureTypeRoutingResolveService } from './expenditure-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const expenditureTypeRoute: Routes = [
  {
    path: '',
    component: ExpenditureTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpenditureTypeDetailComponent,
    resolve: {
      expenditureType: ExpenditureTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpenditureTypeUpdateComponent,
    resolve: {
      expenditureType: ExpenditureTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpenditureTypeUpdateComponent,
    resolve: {
      expenditureType: ExpenditureTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(expenditureTypeRoute)],
  exports: [RouterModule],
})
export class ExpenditureTypeRoutingModule {}
