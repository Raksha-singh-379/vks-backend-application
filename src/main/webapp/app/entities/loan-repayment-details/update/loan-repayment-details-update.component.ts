import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanRepaymentDetailsFormService, LoanRepaymentDetailsFormGroup } from './loan-repayment-details-form.service';
import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import { LoanRepaymentDetailsService } from '../service/loan-repayment-details.service';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

@Component({
  selector: 'jhi-loan-repayment-details-update',
  templateUrl: './loan-repayment-details-update.component.html',
})
export class LoanRepaymentDetailsUpdateComponent implements OnInit {
  isSaving = false;
  loanRepaymentDetails: ILoanRepaymentDetails | null = null;
  paymentModeValues = Object.keys(PaymentMode);

  loanDetailsSharedCollection: ILoanDetails[] = [];

  editForm: LoanRepaymentDetailsFormGroup = this.loanRepaymentDetailsFormService.createLoanRepaymentDetailsFormGroup();

  constructor(
    protected loanRepaymentDetailsService: LoanRepaymentDetailsService,
    protected loanRepaymentDetailsFormService: LoanRepaymentDetailsFormService,
    protected loanDetailsService: LoanDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanDetails = (o1: ILoanDetails | null, o2: ILoanDetails | null): boolean => this.loanDetailsService.compareLoanDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanRepaymentDetails }) => {
      this.loanRepaymentDetails = loanRepaymentDetails;
      if (loanRepaymentDetails) {
        this.updateForm(loanRepaymentDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanRepaymentDetails = this.loanRepaymentDetailsFormService.getLoanRepaymentDetails(this.editForm);
    if (loanRepaymentDetails.id !== null) {
      this.subscribeToSaveResponse(this.loanRepaymentDetailsService.update(loanRepaymentDetails));
    } else {
      this.subscribeToSaveResponse(this.loanRepaymentDetailsService.create(loanRepaymentDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanRepaymentDetails>>): void {
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

  protected updateForm(loanRepaymentDetails: ILoanRepaymentDetails): void {
    this.loanRepaymentDetails = loanRepaymentDetails;
    this.loanRepaymentDetailsFormService.resetForm(this.editForm, loanRepaymentDetails);

    this.loanDetailsSharedCollection = this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(
      this.loanDetailsSharedCollection,
      loanRepaymentDetails.loanDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanDetailsService
      .query()
      .pipe(map((res: HttpResponse<ILoanDetails[]>) => res.body ?? []))
      .pipe(
        map((loanDetails: ILoanDetails[]) =>
          this.loanDetailsService.addLoanDetailsToCollectionIfMissing<ILoanDetails>(loanDetails, this.loanRepaymentDetails?.loanDetails)
        )
      )
      .subscribe((loanDetails: ILoanDetails[]) => (this.loanDetailsSharedCollection = loanDetails));
  }
}
