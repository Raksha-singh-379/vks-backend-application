import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyBanksDetails } from '../society-banks-details.model';

@Component({
  selector: 'jhi-society-banks-details-detail',
  templateUrl: './society-banks-details-detail.component.html',
})
export class SocietyBanksDetailsDetailComponent implements OnInit {
  societyBanksDetails: ISocietyBanksDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyBanksDetails }) => {
      this.societyBanksDetails = societyBanksDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
