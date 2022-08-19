import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyBanksDetailsFormService, SocietyBanksDetailsFormGroup } from './society-banks-details-form.service';
import { ISocietyBanksDetails } from '../society-banks-details.model';
import { SocietyBanksDetailsService } from '../service/society-banks-details.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

@Component({
  selector: 'jhi-society-banks-details-update',
  templateUrl: './society-banks-details-update.component.html',
})
export class SocietyBanksDetailsUpdateComponent implements OnInit {
  isSaving = false;
  societyBanksDetails: ISocietyBanksDetails | null = null;

  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyBanksDetailsFormGroup = this.societyBanksDetailsFormService.createSocietyBanksDetailsFormGroup();

  constructor(
    protected societyBanksDetailsService: SocietyBanksDetailsService,
    protected societyBanksDetailsFormService: SocietyBanksDetailsFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyBanksDetails }) => {
      this.societyBanksDetails = societyBanksDetails;
      if (societyBanksDetails) {
        this.updateForm(societyBanksDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyBanksDetails = this.societyBanksDetailsFormService.getSocietyBanksDetails(this.editForm);
    if (societyBanksDetails.id !== null) {
      this.subscribeToSaveResponse(this.societyBanksDetailsService.update(societyBanksDetails));
    } else {
      this.subscribeToSaveResponse(this.societyBanksDetailsService.create(societyBanksDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyBanksDetails>>): void {
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

  protected updateForm(societyBanksDetails: ISocietyBanksDetails): void {
    this.societyBanksDetails = societyBanksDetails;
    this.societyBanksDetailsFormService.resetForm(this.editForm, societyBanksDetails);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyBanksDetails.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyBanksDetails?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
