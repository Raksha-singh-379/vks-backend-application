import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExpenditureTypeComponent } from './list/expenditure-type.component';
import { ExpenditureTypeDetailComponent } from './detail/expenditure-type-detail.component';
import { ExpenditureTypeUpdateComponent } from './update/expenditure-type-update.component';
import { ExpenditureTypeDeleteDialogComponent } from './delete/expenditure-type-delete-dialog.component';
import { ExpenditureTypeRoutingModule } from './route/expenditure-type-routing.module';

@NgModule({
  imports: [SharedModule, ExpenditureTypeRoutingModule],
  declarations: [
    ExpenditureTypeComponent,
    ExpenditureTypeDetailComponent,
    ExpenditureTypeUpdateComponent,
    ExpenditureTypeDeleteDialogComponent,
  ],
})
export class ExpenditureTypeModule {}
