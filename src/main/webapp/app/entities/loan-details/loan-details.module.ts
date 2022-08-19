import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanDetailsComponent } from './list/loan-details.component';
import { LoanDetailsDetailComponent } from './detail/loan-details-detail.component';
import { LoanDetailsUpdateComponent } from './update/loan-details-update.component';
import { LoanDetailsDeleteDialogComponent } from './delete/loan-details-delete-dialog.component';
import { LoanDetailsRoutingModule } from './route/loan-details-routing.module';

@NgModule({
  imports: [SharedModule, LoanDetailsRoutingModule],
  declarations: [LoanDetailsComponent, LoanDetailsDetailComponent, LoanDetailsUpdateComponent, LoanDetailsDeleteDialogComponent],
})
export class LoanDetailsModule {}
