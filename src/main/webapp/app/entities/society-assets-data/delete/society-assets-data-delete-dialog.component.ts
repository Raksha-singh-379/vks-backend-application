import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyAssetsData } from '../society-assets-data.model';
import { SocietyAssetsDataService } from '../service/society-assets-data.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-assets-data-delete-dialog.component.html',
})
export class SocietyAssetsDataDeleteDialogComponent {
  societyAssetsData?: ISocietyAssetsData;

  constructor(protected societyAssetsDataService: SocietyAssetsDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyAssetsDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
