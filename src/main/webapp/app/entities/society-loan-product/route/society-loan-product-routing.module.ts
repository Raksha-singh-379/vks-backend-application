import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocietyLoanProductComponent } from '../list/society-loan-product.component';
import { SocietyLoanProductDetailComponent } from '../detail/society-loan-product-detail.component';
import { SocietyLoanProductUpdateComponent } from '../update/society-loan-product-update.component';
import { SocietyLoanProductRoutingResolveService } from './society-loan-product-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const societyLoanProductRoute: Routes = [
  {
    path: '',
    component: SocietyLoanProductComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocietyLoanProductDetailComponent,
    resolve: {
      societyLoanProduct: SocietyLoanProductRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocietyLoanProductUpdateComponent,
    resolve: {
      societyLoanProduct: SocietyLoanProductRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocietyLoanProductUpdateComponent,
    resolve: {
      societyLoanProduct: SocietyLoanProductRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(societyLoanProductRoute)],
  exports: [RouterModule],
})
export class SocietyLoanProductRoutingModule {}
