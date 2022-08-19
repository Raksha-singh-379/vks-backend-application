import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankDhoranDetails } from '../bank-dhoran-details.model';

@Component({
  selector: 'jhi-bank-dhoran-details-detail',
  templateUrl: './bank-dhoran-details-detail.component.html',
})
export class BankDhoranDetailsDetailComponent implements OnInit {
  bankDhoranDetails: IBankDhoranDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankDhoranDetails }) => {
      this.bankDhoranDetails = bankDhoranDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
