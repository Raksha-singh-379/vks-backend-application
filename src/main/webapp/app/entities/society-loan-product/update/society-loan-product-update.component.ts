import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyLoanProductFormService, SocietyLoanProductFormGroup } from './society-loan-product-form.service';
import { ISocietyLoanProduct } from '../society-loan-product.model';
import { SocietyLoanProductService } from '../service/society-loan-product.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';
import { IGRInterestDetails } from 'app/entities/gr-interest-details/gr-interest-details.model';
import { GRInterestDetailsService } from 'app/entities/gr-interest-details/service/gr-interest-details.service';

@Component({
  selector: 'jhi-society-loan-product-update',
  templateUrl: './society-loan-product-update.component.html',
})
export class SocietyLoanProductUpdateComponent implements OnInit {
  isSaving = false;
  societyLoanProduct: ISocietyLoanProduct | null = null;

  societiesSharedCollection: ISociety[] = [];
  bankDhoranDetailsSharedCollection: IBankDhoranDetails[] = [];
  gRInterestDetailsSharedCollection: IGRInterestDetails[] = [];

  editForm: SocietyLoanProductFormGroup = this.societyLoanProductFormService.createSocietyLoanProductFormGroup();

  constructor(
    protected societyLoanProductService: SocietyLoanProductService,
    protected societyLoanProductFormService: SocietyLoanProductFormService,
    protected societyService: SocietyService,
    protected bankDhoranDetailsService: BankDhoranDetailsService,
    protected gRInterestDetailsService: GRInterestDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  compareBankDhoranDetails = (o1: IBankDhoranDetails | null, o2: IBankDhoranDetails | null): boolean =>
    this.bankDhoranDetailsService.compareBankDhoranDetails(o1, o2);

  compareGRInterestDetails = (o1: IGRInterestDetails | null, o2: IGRInterestDetails | null): boolean =>
    this.gRInterestDetailsService.compareGRInterestDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyLoanProduct }) => {
      this.societyLoanProduct = societyLoanProduct;
      if (societyLoanProduct) {
        this.updateForm(societyLoanProduct);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyLoanProduct = this.societyLoanProductFormService.getSocietyLoanProduct(this.editForm);
    if (societyLoanProduct.id !== null) {
      this.subscribeToSaveResponse(this.societyLoanProductService.update(societyLoanProduct));
    } else {
      this.subscribeToSaveResponse(this.societyLoanProductService.create(societyLoanProduct));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyLoanProduct>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(societyLoanProduct: ISocietyLoanProduct): void {
    this.societyLoanProduct = societyLoanProduct;
    this.societyLoanProductFormService.resetForm(this.editForm, societyLoanProduct);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyLoanProduct.society
    );
    this.bankDhoranDetailsSharedCollection = this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
      this.bankDhoranDetailsSharedCollection,
      societyLoanProduct.bankDhoranDetails
    );
    this.gRInterestDetailsSharedCollection = this.gRInterestDetailsService.addGRInterestDetailsToCollectionIfMissing<IGRInterestDetails>(
      this.gRInterestDetailsSharedCollection,
      societyLoanProduct.gRInterestDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyLoanProduct?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));

    this.bankDhoranDetailsService
      .query()
      .pipe(map((res: HttpResponse<IBankDhoranDetails[]>) => res.body ?? []))
      .pipe(
        map((bankDhoranDetails: IBankDhoranDetails[]) =>
          this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
            bankDhoranDetails,
            this.societyLoanProduct?.bankDhoranDetails
          )
        )
      )
      .subscribe((bankDhoranDetails: IBankDhoranDetails[]) => (this.bankDhoranDetailsSharedCollection = bankDhoranDetails));

    this.gRInterestDetailsService
      .query()
      .pipe(map((res: HttpResponse<IGRInterestDetails[]>) => res.body ?? []))
      .pipe(
        map((gRInterestDetails: IGRInterestDetails[]) =>
          this.gRInterestDetailsService.addGRInterestDetailsToCollectionIfMissing<IGRInterestDetails>(
            gRInterestDetails,
            this.societyLoanProduct?.gRInterestDetails
          )
        )
      )
      .subscribe((gRInterestDetails: IGRInterestDetails[]) => (this.gRInterestDetailsSharedCollection = gRInterestDetails));
  }
}
