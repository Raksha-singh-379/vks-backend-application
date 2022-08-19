import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanWatapDetails } from '../loan-watap-details.model';
import { LoanWatapDetailsService } from '../service/loan-watap-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-watap-details-delete-dialog.component.html',
})
export class LoanWatapDetailsDeleteDialogComponent {
  loanWatapDetails?: ILoanWatapDetails;

  constructor(protected loanWatapDetailsService: LoanWatapDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanWatapDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
