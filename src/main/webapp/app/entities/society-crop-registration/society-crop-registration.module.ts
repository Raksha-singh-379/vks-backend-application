import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyCropRegistrationComponent } from './list/society-crop-registration.component';
import { SocietyCropRegistrationDetailComponent } from './detail/society-crop-registration-detail.component';
import { SocietyCropRegistrationUpdateComponent } from './update/society-crop-registration-update.component';
import { SocietyCropRegistrationDeleteDialogComponent } from './delete/society-crop-registration-delete-dialog.component';
import { SocietyCropRegistrationRoutingModule } from './route/society-crop-registration-routing.module';

@NgModule({
  imports: [SharedModule, SocietyCropRegistrationRoutingModule],
  declarations: [
    SocietyCropRegistrationComponent,
    SocietyCropRegistrationDetailComponent,
    SocietyCropRegistrationUpdateComponent,
    SocietyCropRegistrationDeleteDialogComponent,
  ],
})
export class SocietyCropRegistrationModule {}
