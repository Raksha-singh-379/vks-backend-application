import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyAssetsComponent } from './list/society-assets.component';
import { SocietyAssetsDetailComponent } from './detail/society-assets-detail.component';
import { SocietyAssetsUpdateComponent } from './update/society-assets-update.component';
import { SocietyAssetsDeleteDialogComponent } from './delete/society-assets-delete-dialog.component';
import { SocietyAssetsRoutingModule } from './route/society-assets-routing.module';

@NgModule({
  imports: [SharedModule, SocietyAssetsRoutingModule],
  declarations: [SocietyAssetsComponent, SocietyAssetsDetailComponent, SocietyAssetsUpdateComponent, SocietyAssetsDeleteDialogComponent],
})
export class SocietyAssetsModule {}
