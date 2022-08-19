import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyAssets } from '../society-assets.model';
import { SocietyAssetsService } from '../service/society-assets.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-assets-delete-dialog.component.html',
})
export class SocietyAssetsDeleteDialogComponent {
  societyAssets?: ISocietyAssets;

  constructor(protected societyAssetsService: SocietyAssetsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyAssetsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
