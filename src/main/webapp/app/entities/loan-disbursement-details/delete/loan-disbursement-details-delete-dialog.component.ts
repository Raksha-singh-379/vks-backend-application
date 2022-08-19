import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import { LoanDisbursementDetailsService } from '../service/loan-disbursement-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-disbursement-details-delete-dialog.component.html',
})
export class LoanDisbursementDetailsDeleteDialogComponent {
  loanDisbursementDetails?: ILoanDisbursementDetails;

  constructor(protected loanDisbursementDetailsService: LoanDisbursementDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanDisbursementDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
