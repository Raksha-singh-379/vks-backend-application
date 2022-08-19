import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanDemandComponent } from './list/loan-demand.component';
import { LoanDemandDetailComponent } from './detail/loan-demand-detail.component';
import { LoanDemandUpdateComponent } from './update/loan-demand-update.component';
import { LoanDemandDeleteDialogComponent } from './delete/loan-demand-delete-dialog.component';
import { LoanDemandRoutingModule } from './route/loan-demand-routing.module';

@NgModule({
  imports: [SharedModule, LoanDemandRoutingModule],
  declarations: [LoanDemandComponent, LoanDemandDetailComponent, LoanDemandUpdateComponent, LoanDemandDeleteDialogComponent],
})
export class LoanDemandModule {}
