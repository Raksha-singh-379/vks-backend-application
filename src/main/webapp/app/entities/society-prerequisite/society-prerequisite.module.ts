import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocietyPrerequisiteComponent } from './list/society-prerequisite.component';
import { SocietyPrerequisiteDetailComponent } from './detail/society-prerequisite-detail.component';
import { SocietyPrerequisiteUpdateComponent } from './update/society-prerequisite-update.component';
import { SocietyPrerequisiteDeleteDialogComponent } from './delete/society-prerequisite-delete-dialog.component';
import { SocietyPrerequisiteRoutingModule } from './route/society-prerequisite-routing.module';

@NgModule({
  imports: [SharedModule, SocietyPrerequisiteRoutingModule],
  declarations: [
    SocietyPrerequisiteComponent,
    SocietyPrerequisiteDetailComponent,
    SocietyPrerequisiteUpdateComponent,
    SocietyPrerequisiteDeleteDialogComponent,
  ],
})
export class SocietyPrerequisiteModule {}
