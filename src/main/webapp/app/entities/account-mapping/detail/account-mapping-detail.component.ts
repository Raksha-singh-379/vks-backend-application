import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountMapping } from '../account-mapping.model';

@Component({
  selector: 'jhi-account-mapping-detail',
  templateUrl: './account-mapping-detail.component.html',
})
export class AccountMappingDetailComponent implements OnInit {
  accountMapping: IAccountMapping | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountMapping }) => {
      this.accountMapping = accountMapping;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
