import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberLandAssets } from '../member-land-assets.model';

@Component({
  selector: 'jhi-member-land-assets-detail',
  templateUrl: './member-land-assets-detail.component.html',
})
export class MemberLandAssetsDetailComponent implements OnInit {
  memberLandAssets: IMemberLandAssets | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLandAssets }) => {
      this.memberLandAssets = memberLandAssets;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
