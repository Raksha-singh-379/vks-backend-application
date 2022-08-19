import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BankDhoranDetailsFormService, BankDhoranDetailsFormGroup } from './bank-dhoran-details-form.service';
import { IBankDhoranDetails } from '../bank-dhoran-details.model';
import { BankDhoranDetailsService } from '../service/bank-dhoran-details.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

@Component({
  selector: 'jhi-bank-dhoran-details-update',
  templateUrl: './bank-dhoran-details-update.component.html',
})
export class BankDhoranDetailsUpdateComponent implements OnInit {
  isSaving = false;
  bankDhoranDetails: IBankDhoranDetails | null = null;

  societiesSharedCollection: ISociety[] = [];

  editForm: BankDhoranDetailsFormGroup = this.bankDhoranDetailsFormService.createBankDhoranDetailsFormGroup();

  constructor(
    protected bankDhoranDetailsService: BankDhoranDetailsService,
    protected bankDhoranDetailsFormService: BankDhoranDetailsFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankDhoranDetails }) => {
      this.bankDhoranDetails = bankDhoranDetails;
      if (bankDhoranDetails) {
        this.updateForm(bankDhoranDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankDhoranDetails = this.bankDhoranDetailsFormService.getBankDhoranDetails(this.editForm);
    if (bankDhoranDetails.id !== null) {
      this.subscribeToSaveResponse(this.bankDhoranDetailsService.update(bankDhoranDetails));
    } else {
      this.subscribeToSaveResponse(this.bankDhoranDetailsService.create(bankDhoranDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankDhoranDetails>>): void {
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

  protected updateForm(bankDhoranDetails: IBankDhoranDetails): void {
    this.bankDhoranDetails = bankDhoranDetails;
    this.bankDhoranDetailsFormService.resetForm(this.editForm, bankDhoranDetails);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      bankDhoranDetails.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.bankDhoranDetails?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
