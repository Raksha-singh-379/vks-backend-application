import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyLoanProduct } from '../society-loan-product.model';
import { SocietyLoanProductService } from '../service/society-loan-product.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-loan-product-delete-dialog.component.html',
})
export class SocietyLoanProductDeleteDialogComponent {
  societyLoanProduct?: ISocietyLoanProduct;

  constructor(protected societyLoanProductService: SocietyLoanProductService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyLoanProductService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
