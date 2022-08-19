import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccountMappingComponent } from '../list/account-mapping.component';
import { AccountMappingDetailComponent } from '../detail/account-mapping-detail.component';
import { AccountMappingUpdateComponent } from '../update/account-mapping-update.component';
import { AccountMappingRoutingResolveService } from './account-mapping-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const accountMappingRoute: Routes = [
  {
    path: '',
    component: AccountMappingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountMappingDetailComponent,
    resolve: {
      accountMapping: AccountMappingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountMappingUpdateComponent,
    resolve: {
      accountMapping: AccountMappingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountMappingUpdateComponent,
    resolve: {
      accountMapping: AccountMappingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accountMappingRoute)],
  exports: [RouterModule],
})
export class AccountMappingRoutingModule {}
