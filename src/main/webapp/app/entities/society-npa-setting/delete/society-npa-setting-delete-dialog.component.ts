import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { SocietyNpaSettingService } from '../service/society-npa-setting.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-npa-setting-delete-dialog.component.html',
})
export class SocietyNpaSettingDeleteDialogComponent {
  societyNpaSetting?: ISocietyNpaSetting;

  constructor(protected societyNpaSettingService: SocietyNpaSettingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyNpaSettingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
