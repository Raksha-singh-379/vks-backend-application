import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanWatapDetailsComponent } from './list/loan-watap-details.component';
import { LoanWatapDetailsDetailComponent } from './detail/loan-watap-details-detail.component';
import { LoanWatapDetailsUpdateComponent } from './update/loan-watap-details-update.component';
import { LoanWatapDetailsDeleteDialogComponent } from './delete/loan-watap-details-delete-dialog.component';
import { LoanWatapDetailsRoutingModule } from './route/loan-watap-details-routing.module';

@NgModule({
  imports: [SharedModule, LoanWatapDetailsRoutingModule],
  declarations: [
    LoanWatapDetailsComponent,
    LoanWatapDetailsDetailComponent,
    LoanWatapDetailsUpdateComponent,
    LoanWatapDetailsDeleteDialogComponent,
  ],
})
export class LoanWatapDetailsModule {}
