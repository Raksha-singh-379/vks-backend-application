import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanDisbursementDetailsFormService, LoanDisbursementDetailsFormGroup } from './loan-disbursement-details-form.service';
import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import { LoanDisbursementDetailsService } from '../service/loan-disbursement-details.service';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';
import { LoanType } from 'app/entities/enumerations/loan-type.model';

@Component({
  selector: 'jhi-loan-disbursement-details-update',
  templateUrl: './loan-disbursement-details-update.component.html',
})
export class LoanDisbursementDetailsUpdateComponent implements OnInit {
  isSaving = false;
  loanDisbursementDetails: ILoanDisbursementDetails | null = null;
  paymentModeValues = Object.keys(PaymentMode);
  loanTypeValues = Object.keys(LoanType);

  loanDetailsSharedCollection: ILoanDetails[] = [];

  editForm: LoanDisbursementDetailsFormGroup = this.loanDisbursementDetailsFormService.createLoanDisbursementDetailsFormGroup();

  constructor(
    protected loanDisbursementDetailsService: LoanDisbursementDetailsService,
    protected loanDisbursementDetailsFormService: LoanDisbursementDetailsFormService,
    protected loanDetailsService: LoanDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanDetails = (o1: ILoanDetails | null, o2: ILoanDetails | null): boolean => this.loanDetailsService.compareLoanDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDisbursementDetails }) => {
      this.loanDisbursementDetails = loanDisbursementDetails;
      if (loanDisbursementDetails) {
        this.updateForm(loanDisbursementDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanDisbursementDetails = this.loanDisbursementDetailsFormService.getLoanDisbursementDetails(this.editForm);
    if (loanDisbursementDetails.id !== null) {
      this.subscribeToSaveResponse(this.loanDisbursementDetailsService.update(loanDisbursementDetails));
    } else {
      this.subscribeToSaveResponse(this.loanDisbursementDetailsService.create(loanDisbursementDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanDisbursementDetails>>): void {
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

  protected updateForm(loanDisbursementDetails: ILoanDisbursementDetails): void {
    this.loanDisbursementDetails = loanDisbursementDetails;
    this.loanDisbursementDetailsFormService.resetForm(this.editForm, loanDisbursementDetails);

    this.loanDetailsSharedCollection = this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(
      this.loanDetailsSharedCollection,
      loanDisbursementDetails.loanDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanDetailsService
      .query()
      .pipe(map((res: HttpResponse<ILoanDetails[]>) => res.body ?? []))
      .pipe(
        map((loanDetails: ILoanDetails[]) =>
          this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(loanDetails, this.loanDisbursementDetails?.loanDetails)
        )
      )
      .subscribe((loanDetails: ILoanDetails[]) => (this.loanDetailsSharedCollection = loanDetails));
  }
}
