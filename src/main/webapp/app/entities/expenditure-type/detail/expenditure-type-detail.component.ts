import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpenditureType } from '../expenditure-type.model';

@Component({
  selector: 'jhi-expenditure-type-detail',
  templateUrl: './expenditure-type-detail.component.html',
})
export class ExpenditureTypeDetailComponent implements OnInit {
  expenditureType: IExpenditureType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expenditureType }) => {
      this.expenditureType = expenditureType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
