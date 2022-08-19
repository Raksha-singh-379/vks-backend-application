import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { SocietyPrerequisiteService } from '../service/society-prerequisite.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-prerequisite-delete-dialog.component.html',
})
export class SocietyPrerequisiteDeleteDialogComponent {
  societyPrerequisite?: ISocietyPrerequisite;

  constructor(protected societyPrerequisiteService: SocietyPrerequisiteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyPrerequisiteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
