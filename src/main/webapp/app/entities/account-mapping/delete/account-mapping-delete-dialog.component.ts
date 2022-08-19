import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountMapping } from '../account-mapping.model';
import { AccountMappingService } from '../service/account-mapping.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './account-mapping-delete-dialog.component.html',
})
export class AccountMappingDeleteDialogComponent {
  accountMapping?: IAccountMapping;

  constructor(protected accountMappingService: AccountMappingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountMappingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
