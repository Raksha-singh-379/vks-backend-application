import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyAssetsDataComponent } from '../list/society-assets-data.component';
import { SocietyAssetsDataDetailComponent } from '../detail/society-assets-data-detail.component';
import { SocietyAssetsDataUpdateComponent } from '../update/society-assets-data-update.component';
import { SocietyAssetsDataRoutingResolveService } from './society-assets-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyAssetsDataRoute: Routes = [
  {
    path: '',
    component: SocietyAssetsDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyAssetsDataDetailComponent,
    resolve: {
      societyAssetsData: SocietyAssetsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyAssetsDataUpdateComponent,
    resolve: {
      societyAssetsData: SocietyAssetsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyAssetsDataUpdateComponent,
    resolve: {
      societyAssetsData: SocietyAssetsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyAssetsDataRoute)],
  exports: [RouterModule],
})
export class SocietyAssetsDataRoutingModule {}
