import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyBanksDetailsComponent } from './list/society-banks-details.component';
import { SocietyBanksDetailsDetailComponent } from './detail/society-banks-details-detail.component';
import { SocietyBanksDetailsUpdateComponent } from './update/society-banks-details-update.component';
import { SocietyBanksDetailsDeleteDialogComponent } from './delete/society-banks-details-delete-dialog.component';
import { SocietyBanksDetailsRoutingModule } from './route/society-banks-details-routing.module';

@NgModule({
  imports: [SharedModule, SocietyBanksDetailsRoutingModule],
  declarations: [
    SocietyBanksDetailsComponent,
    SocietyBanksDetailsDetailComponent,
    SocietyBanksDetailsUpdateComponent,
    SocietyBanksDetailsDeleteDialogComponent,
  ],
})
export class SocietyBanksDetailsModule {}
