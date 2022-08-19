import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyConfig } from '../society-config.model';
import { SocietyConfigService } from '../service/society-config.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-config-delete-dialog.component.html',
})
export class SocietyConfigDeleteDialogComponent {
  societyConfig?: ISocietyConfig;

  constructor(protected societyConfigService: SocietyConfigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
