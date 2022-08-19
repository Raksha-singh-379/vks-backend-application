import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyAssets } from '../society-assets.model';

@Component({
  selector: 'jhi-society-assets-detail',
  templateUrl: './society-assets-detail.component.html',
})
export class SocietyAssetsDetailComponent implements OnInit {
  societyAssets: ISocietyAssets | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyAssets }) => {
      this.societyAssets = societyAssets;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
