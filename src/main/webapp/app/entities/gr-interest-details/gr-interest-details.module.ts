import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GRInterestDetailsComponent } from './list/gr-interest-details.component';
import { GRInterestDetailsDetailComponent } from './detail/gr-interest-details-detail.component';
import { GRInterestDetailsUpdateComponent } from './update/gr-interest-details-update.component';
import { GRInterestDetailsDeleteDialogComponent } from './delete/gr-interest-details-delete-dialog.component';
import { GRInterestDetailsRoutingModule } from './route/gr-interest-details-routing.module';

@NgModule({
  imports: [SharedModule, GRInterestDetailsRoutingModule],
  declarations: [
    GRInterestDetailsComponent,
    GRInterestDetailsDetailComponent,
    GRInterestDetailsUpdateComponent,
    GRInterestDetailsDeleteDialogComponent,
  ],
})
export class GRInterestDetailsModule {}
