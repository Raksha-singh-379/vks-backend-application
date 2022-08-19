import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyCropRegistration } from '../society-crop-registration.model';

@Component({
  selector: 'jhi-society-crop-registration-detail',
  templateUrl: './society-crop-registration-detail.component.html',
})
export class SocietyCropRegistrationDetailComponent implements OnInit {
  societyCropRegistration: ISocietyCropRegistration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyCropRegistration }) => {
      this.societyCropRegistration = societyCropRegistration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
