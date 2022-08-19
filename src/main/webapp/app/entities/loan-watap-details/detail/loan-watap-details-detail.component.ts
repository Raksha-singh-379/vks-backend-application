import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanWatapDetails } from '../loan-watap-details.model';

@Component({
  selector: 'jhi-loan-watap-details-detail',
  templateUrl: './loan-watap-details-detail.component.html',
})
export class LoanWatapDetailsDetailComponent implements OnInit {
  loanWatapDetails: ILoanWatapDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanWatapDetails }) => {
      this.loanWatapDetails = loanWatapDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
