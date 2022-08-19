import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanDemand } from '../loan-demand.model';

@Component({
  selector: 'jhi-loan-demand-detail',
  templateUrl: './loan-demand-detail.component.html',
})
export class LoanDemandDetailComponent implements OnInit {
  loanDemand: ILoanDemand | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDemand }) => {
      this.loanDemand = loanDemand;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
