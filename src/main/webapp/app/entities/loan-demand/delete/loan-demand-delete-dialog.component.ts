import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanDemand } from '../loan-demand.model';
import { LoanDemandService } from '../service/loan-demand.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-demand-delete-dialog.component.html',
})
export class LoanDemandDeleteDialogComponent {
  loanDemand?: ILoanDemand;

  constructor(protected loanDemandService: LoanDemandService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanDemandService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
