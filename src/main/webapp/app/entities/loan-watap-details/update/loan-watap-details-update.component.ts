import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanWatapDetailsFormService, LoanWatapDetailsFormGroup } from './loan-watap-details-form.service';
import { ILoanWatapDetails } from '../loan-watap-details.model';
import { LoanWatapDetailsService } from '../service/loan-watap-details.service';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { LoanDemandService } from 'app/entities/loan-demand/service/loan-demand.service';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

@Component({
  selector: 'jhi-loan-watap-details-update',
  templateUrl: './loan-watap-details-update.component.html',
})
export class LoanWatapDetailsUpdateComponent implements OnInit {
  isSaving = false;
  loanWatapDetails: ILoanWatapDetails | null = null;
  loanStatusValues = Object.keys(LoanStatus);

  loanDemandsSharedCollection: ILoanDemand[] = [];

  editForm: LoanWatapDetailsFormGroup = this.loanWatapDetailsFormService.createLoanWatapDetailsFormGroup();

  constructor(
    protected loanWatapDetailsService: LoanWatapDetailsService,
    protected loanWatapDetailsFormService: LoanWatapDetailsFormService,
    protected loanDemandService: LoanDemandService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanDemand = (o1: ILoanDemand | null, o2: ILoanDemand | null): boolean => this.loanDemandService.compareLoanDemand(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanWatapDetails }) => {
      this.loanWatapDetails = loanWatapDetails;
      if (loanWatapDetails) {
        this.updateForm(loanWatapDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanWatapDetails = this.loanWatapDetailsFormService.getLoanWatapDetails(this.editForm);
    if (loanWatapDetails.id !== null) {
      this.subscribeToSaveResponse(this.loanWatapDetailsService.update(loanWatapDetails));
    } else {
      this.subscribeToSaveResponse(this.loanWatapDetailsService.create(loanWatapDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanWatapDetails>>): void {
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

  protected updateForm(loanWatapDetails: ILoanWatapDetails): void {
    this.loanWatapDetails = loanWatapDetails;
    this.loanWatapDetailsFormService.resetForm(this.editForm, loanWatapDetails);

    this.loanDemandsSharedCollection = this.loanDemandService.addLoanDemandToCollectionIfMissing<ILoanDemand>(
      this.loanDemandsSharedCollection,
      loanWatapDetails.loanDemand
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanDemandService
      .query()
      .pipe(map((res: HttpResponse<ILoanDemand[]>) => res.body ?? []))
      .pipe(
        map((loanDemands: ILoanDemand[]) =>
          this.loanDemandService.addLoanDemandToCollectionIfMissing<ILoanDemand>(loanDemands, this.loanWatapDetails?.loanDemand)
        )
      )
      .subscribe((loanDemands: ILoanDemand[]) => (this.loanDemandsSharedCollection = loanDemands));
  }
}
