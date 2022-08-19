import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyLoanProductComponent } from './list/society-loan-product.component';
import { SocietyLoanProductDetailComponent } from './detail/society-loan-product-detail.component';
import { SocietyLoanProductUpdateComponent } from './update/society-loan-product-update.component';
import { SocietyLoanProductDeleteDialogComponent } from './delete/society-loan-product-delete-dialog.component';
import { SocietyLoanProductRoutingModule } from './route/society-loan-product-routing.module';

@NgModule({
  imports: [SharedModule, SocietyLoanProductRoutingModule],
  declarations: [
    SocietyLoanProductComponent,
    SocietyLoanProductDetailComponent,
    SocietyLoanProductUpdateComponent,
    SocietyLoanProductDeleteDialogComponent,
  ],
})
export class SocietyLoanProductModule {}
