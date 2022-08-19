import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyBanksDetails } from '../society-banks-details.model';
import { SocietyBanksDetailsService } from '../service/society-banks-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-banks-details-delete-dialog.component.html',
})
export class SocietyBanksDetailsDeleteDialogComponent {
  societyBanksDetails?: ISocietyBanksDetails;

  constructor(protected societyBanksDetailsService: SocietyBanksDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyBanksDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
