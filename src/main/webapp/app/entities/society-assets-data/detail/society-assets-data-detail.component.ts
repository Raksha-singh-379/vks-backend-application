import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyAssetsData } from '../society-assets-data.model';

@Component({
  selector: 'jhi-society-assets-data-detail',
  templateUrl: './society-assets-data-detail.component.html',
})
export class SocietyAssetsDataDetailComponent implements OnInit {
  societyAssetsData: ISocietyAssetsData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyAssetsData }) => {
      this.societyAssetsData = societyAssetsData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
