import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanDetails } from '../loan-details.model';
import { LoanDetailsService } from '../service/loan-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-details-delete-dialog.component.html',
})
export class LoanDetailsDeleteDialogComponent {
  loanDetails?: ILoanDetails;

  constructor(protected loanDetailsService: LoanDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
