import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyPrerequisite } from '../society-prerequisite.model';

@Component({
  selector: 'jhi-society-prerequisite-detail',
  templateUrl: './society-prerequisite-detail.component.html',
})
export class SocietyPrerequisiteDetailComponent implements OnInit {
  societyPrerequisite: ISocietyPrerequisite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyPrerequisite }) => {
      this.societyPrerequisite = societyPrerequisite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
