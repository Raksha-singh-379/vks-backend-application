import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccountMappingComponent } from './list/account-mapping.component';
import { AccountMappingDetailComponent } from './detail/account-mapping-detail.component';
import { AccountMappingUpdateComponent } from './update/account-mapping-update.component';
import { AccountMappingDeleteDialogComponent } from './delete/account-mapping-delete-dialog.component';
import { AccountMappingRoutingModule } from './route/account-mapping-routing.module';

@NgModule({
  imports: [SharedModule, AccountMappingRoutingModule],
  declarations: [
    AccountMappingComponent,
    AccountMappingDetailComponent,
    AccountMappingUpdateComponent,
    AccountMappingDeleteDialogComponent,
  ],
})
export class AccountMappingModule {}
