import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanDisbursementDetailsComponent } from './list/loan-disbursement-details.component';
import { LoanDisbursementDetailsDetailComponent } from './detail/loan-disbursement-details-detail.component';
import { LoanDisbursementDetailsUpdateComponent } from './update/loan-disbursement-details-update.component';
import { LoanDisbursementDetailsDeleteDialogComponent } from './delete/loan-disbursement-details-delete-dialog.component';
import { LoanDisbursementDetailsRoutingModule } from './route/loan-disbursement-details-routing.module';

@NgModule({
  imports: [SharedModule, LoanDisbursementDetailsRoutingModule],
  declarations: [
    LoanDisbursementDetailsComponent,
    LoanDisbursementDetailsDetailComponent,
    LoanDisbursementDetailsUpdateComponent,
    LoanDisbursementDetailsDeleteDialogComponent,
  ],
})
export class LoanDisbursementDetailsModule {}
