import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyAssetsDataComponent } from './list/society-assets-data.component';
import { SocietyAssetsDataDetailComponent } from './detail/society-assets-data-detail.component';
import { SocietyAssetsDataUpdateComponent } from './update/society-assets-data-update.component';
import { SocietyAssetsDataDeleteDialogComponent } from './delete/society-assets-data-delete-dialog.component';
import { SocietyAssetsDataRoutingModule } from './route/society-assets-data-routing.module';

@NgModule({
  imports: [SharedModule, SocietyAssetsDataRoutingModule],
  declarations: [
    SocietyAssetsDataComponent,
    SocietyAssetsDataDetailComponent,
    SocietyAssetsDataUpdateComponent,
    SocietyAssetsDataDeleteDialogComponent,
  ],
})
export class SocietyAssetsDataModule {}
