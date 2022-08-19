import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';

@Component({
  selector: 'jhi-loan-disbursement-details-detail',
  templateUrl: './loan-disbursement-details-detail.component.html',
})
export class LoanDisbursementDetailsDetailComponent implements OnInit {
  loanDisbursementDetails: ILoanDisbursementDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDisbursementDetails }) => {
      this.loanDisbursementDetails = loanDisbursementDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
