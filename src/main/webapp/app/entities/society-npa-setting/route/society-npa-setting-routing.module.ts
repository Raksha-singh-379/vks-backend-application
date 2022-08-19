import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyNpaSettingComponent } from '../list/society-npa-setting.component';
import { SocietyNpaSettingDetailComponent } from '../detail/society-npa-setting-detail.component';
import { SocietyNpaSettingUpdateComponent } from '../update/society-npa-setting-update.component';
import { SocietyNpaSettingRoutingResolveService } from './society-npa-setting-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyNpaSettingRoute: Routes = [
  {
    path: '',
    component: SocietyNpaSettingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyNpaSettingDetailComponent,
    resolve: {
      societyNpaSetting: SocietyNpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyNpaSettingUpdateComponent,
    resolve: {
      societyNpaSetting: SocietyNpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyNpaSettingUpdateComponent,
    resolve: {
      societyNpaSetting: SocietyNpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyNpaSettingRoute)],
  exports: [RouterModule],
})
export class SocietyNpaSettingRoutingModule {}
