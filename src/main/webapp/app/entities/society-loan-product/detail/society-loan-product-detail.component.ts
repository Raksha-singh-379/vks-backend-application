import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocietyLoanProduct } from '../society-loan-product.model';

@Component({
  selector: 'jhi-society-loan-product-detail',
  templateUrl: './society-loan-product-detail.component.html',
})
export class SocietyLoanProductDetailComponent implements OnInit {
  societyLoanProduct: ISocietyLoanProduct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyLoanProduct }) => {
      this.societyLoanProduct = societyLoanProduct;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
