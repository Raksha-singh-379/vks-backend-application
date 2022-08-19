import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyNpaSettingComponent } from './list/society-npa-setting.component';
import { SocietyNpaSettingDetailComponent } from './detail/society-npa-setting-detail.component';
import { SocietyNpaSettingUpdateComponent } from './update/society-npa-setting-update.component';
import { SocietyNpaSettingDeleteDialogComponent } from './delete/society-npa-setting-delete-dialog.component';
import { SocietyNpaSettingRoutingModule } from './route/society-npa-setting-routing.module';

@NgModule({
  imports: [SharedModule, SocietyNpaSettingRoutingModule],
  declarations: [
    SocietyNpaSettingComponent,
    SocietyNpaSettingDetailComponent,
    SocietyNpaSettingUpdateComponent,
    SocietyNpaSettingDeleteDialogComponent,
  ],
})
export class SocietyNpaSettingModule {}
