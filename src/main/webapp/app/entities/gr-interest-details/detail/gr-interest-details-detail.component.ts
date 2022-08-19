import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGRInterestDetails } from '../gr-interest-details.model';

@Component({
  selector: 'jhi-gr-interest-details-detail',
  templateUrl: './gr-interest-details-detail.component.html',
})
export class GRInterestDetailsDetailComponent implements OnInit {
  gRInterestDetails: IGRInterestDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gRInterestDetails }) => {
      this.gRInterestDetails = gRInterestDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
