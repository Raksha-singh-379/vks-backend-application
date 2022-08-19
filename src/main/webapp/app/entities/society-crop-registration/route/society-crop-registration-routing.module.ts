import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyCropRegistrationComponent } from '../list/society-crop-registration.component';
import { SocietyCropRegistrationDetailComponent } from '../detail/society-crop-registration-detail.component';
import { SocietyCropRegistrationUpdateComponent } from '../update/society-crop-registration-update.component';
import { SocietyCropRegistrationRoutingResolveService } from './society-crop-registration-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyCropRegistrationRoute: Routes = [
  {
    path: '',
    component: SocietyCropRegistrationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyCropRegistrationDetailComponent,
    resolve: {
      societyCropRegistration: SocietyCropRegistrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyCropRegistrationUpdateComponent,
    resolve: {
      societyCropRegistration: SocietyCropRegistrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyCropRegistrationUpdateComponent,
    resolve: {
      societyCropRegistration: SocietyCropRegistrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyCropRegistrationRoute)],
  exports: [RouterModule],
})
export class SocietyCropRegistrationRoutingModule {}
