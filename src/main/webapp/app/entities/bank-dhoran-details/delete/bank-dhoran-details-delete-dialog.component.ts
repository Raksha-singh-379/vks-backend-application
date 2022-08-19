import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankDhoranDetails } from '../bank-dhoran-details.model';
import { BankDhoranDetailsService } from '../service/bank-dhoran-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './bank-dhoran-details-delete-dialog.component.html',
})
export class BankDhoranDetailsDeleteDialogComponent {
  bankDhoranDetails?: IBankDhoranDetails;

  constructor(protected bankDhoranDetailsService: BankDhoranDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankDhoranDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
