import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocietyCropRegistration } from '../society-crop-registration.model';
import { SocietyCropRegistrationService } from '../service/society-crop-registration.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './society-crop-registration-delete-dialog.component.html',
})
export class SocietyCropRegistrationDeleteDialogComponent {
  societyCropRegistration?: ISocietyCropRegistration;

  constructor(protected societyCropRegistrationService: SocietyCropRegistrationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societyCropRegistrationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
