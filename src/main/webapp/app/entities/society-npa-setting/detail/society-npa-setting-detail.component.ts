import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyNpaSetting } from '../society-npa-setting.model';

@Component({
  selector: 'jhi-society-npa-setting-detail',
  templateUrl: './society-npa-setting-detail.component.html',
})
export class SocietyNpaSettingDetailComponent implements OnInit {
  societyNpaSetting: ISocietyNpaSetting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyNpaSetting }) => {
      this.societyNpaSetting = societyNpaSetting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
