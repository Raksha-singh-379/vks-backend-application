import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankDhoranDetailsComponent } from './list/bank-dhoran-details.component';
import { BankDhoranDetailsDetailComponent } from './detail/bank-dhoran-details-detail.component';
import { BankDhoranDetailsUpdateComponent } from './update/bank-dhoran-details-update.component';
import { BankDhoranDetailsDeleteDialogComponent } from './delete/bank-dhoran-details-delete-dialog.component';
import { BankDhoranDetailsRoutingModule } from './route/bank-dhoran-details-routing.module';

@NgModule({
  imports: [SharedModule, BankDhoranDetailsRoutingModule],
  declarations: [
    BankDhoranDetailsComponent,
    BankDhoranDetailsDetailComponent,
    BankDhoranDetailsUpdateComponent,
    BankDhoranDetailsDeleteDialogComponent,
  ],
})
export class BankDhoranDetailsModule {}
