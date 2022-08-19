import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGRInterestDetails } from '../gr-interest-details.model';
import { GRInterestDetailsService } from '../service/gr-interest-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './gr-interest-details-delete-dialog.component.html',
})
export class GRInterestDetailsDeleteDialogComponent {
  gRInterestDetails?: IGRInterestDetails;

  constructor(protected gRInterestDetailsService: GRInterestDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gRInterestDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
