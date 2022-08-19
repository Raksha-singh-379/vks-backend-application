import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyAssetsComponent } from '../list/society-assets.component';
import { SocietyAssetsDetailComponent } from '../detail/society-assets-detail.component';
import { SocietyAssetsUpdateComponent } from '../update/society-assets-update.component';
import { SocietyAssetsRoutingResolveService } from './society-assets-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyAssetsRoute: Routes = [
  {
    path: '',
    component: SocietyAssetsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyAssetsDetailComponent,
    resolve: {
      societyAssets: SocietyAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyAssetsUpdateComponent,
    resolve: {
      societyAssets: SocietyAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyAssetsUpdateComponent,
    resolve: {
      societyAssets: SocietyAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyAssetsRoute)],
  exports: [RouterModule],
})
export class SocietyAssetsRoutingModule {}
