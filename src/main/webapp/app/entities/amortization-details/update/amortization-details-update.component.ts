import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AmortizationDetailsFormService, AmortizationDetailsFormGroup } from './amortization-details-form.service';
import { IAmortizationDetails } from '../amortization-details.model';
import { AmortizationDetailsService } from '../service/amortization-details.service';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';

@Component({
  selector: 'jhi-amortization-details-update',
  templateUrl: './amortization-details-update.component.html',
})
export class AmortizationDetailsUpdateComponent implements OnInit {
  isSaving = false;
  amortizationDetails: IAmortizationDetails | null = null;

  loanDetailsSharedCollection: ILoanDetails[] = [];

  editForm: AmortizationDetailsFormGroup = this.amortizationDetailsFormService.createAmortizationDetailsFormGroup();

  constructor(
    protected amortizationDetailsService: AmortizationDetailsService,
    protected amortizationDetailsFormService: AmortizationDetailsFormService,
    protected loanDetailsService: LoanDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanDetails = (o1: ILoanDetails | null, o2: ILoanDetails | null): boolean => this.loanDetailsService.compareLoanDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ amortizationDetails }) => {
      this.amortizationDetails = amortizationDetails;
      if (amortizationDetails) {
        this.updateForm(amortizationDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const amortizationDetails = this.amortizationDetailsFormService.getAmortizationDetails(this.editForm);
    if (amortizationDetails.id !== null) {
      this.subscribeToSaveResponse(this.amortizationDetailsService.update(amortizationDetails));
    } else {
      this.subscribeToSaveResponse(this.amortizationDetailsService.create(amortizationDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationDetails>>): void {
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

  protected updateForm(amortizationDetails: IAmortizationDetails): void {
    this.amortizationDetails = amortizationDetails;
    this.amortizationDetailsFormService.resetForm(this.editForm, amortizationDetails);

    this.loanDetailsSharedCollection = this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(
      this.loanDetailsSharedCollection,
      amortizationDetails.loanDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanDetailsService
      .query()
      .pipe(map((res: HttpResponse<ILoanDetails[]>) => res.body ?? []))
      .pipe(
        map((loanDetails: ILoanDetails[]) =>
          this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(loanDetails, this.amortizationDetails?.loanDetails)
        )
      )
      .subscribe((loanDetails: ILoanDetails[]) => (this.loanDetailsSharedCollection = loanDetails));
  }
}
