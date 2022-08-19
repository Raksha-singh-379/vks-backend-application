import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyConfig } from '../society-config.model';

@Component({
  selector: 'jhi-society-config-detail',
  templateUrl: './society-config-detail.component.html',
})
export class SocietyConfigDetailComponent implements OnInit {
  societyConfig: ISocietyConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyConfig }) => {
      this.societyConfig = societyConfig;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
