import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanDetails } from '../loan-details.model';

@Component({
  selector: 'jhi-loan-details-detail',
  templateUrl: './loan-details-detail.component.html',
})
export class LoanDetailsDetailComponent implements OnInit {
  loanDetails: ILoanDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDetails }) => {
      this.loanDetails = loanDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
