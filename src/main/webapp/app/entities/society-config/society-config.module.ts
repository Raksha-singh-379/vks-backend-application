import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyConfigComponent } from './list/society-config.component';
import { SocietyConfigDetailComponent } from './detail/society-config-detail.component';
import { SocietyConfigUpdateComponent } from './update/society-config-update.component';
import { SocietyConfigDeleteDialogComponent } from './delete/society-config-delete-dialog.component';
import { SocietyConfigRoutingModule } from './route/society-config-routing.module';

@NgModule({
  imports: [SharedModule, SocietyConfigRoutingModule],
  declarations: [SocietyConfigComponent, SocietyConfigDetailComponent, SocietyConfigUpdateComponent, SocietyConfigDeleteDialogComponent],
})
export class SocietyConfigModule {}
