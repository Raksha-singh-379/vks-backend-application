import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExpenditureType } from '../expenditure-type.model';
import { ExpenditureTypeService } from '../service/expenditure-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './expenditure-type-delete-dialog.component.html',
})
export class ExpenditureTypeDeleteDialogComponent {
  expenditureType?: IExpenditureType;

  constructor(protected expenditureTypeService: ExpenditureTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expenditureTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
