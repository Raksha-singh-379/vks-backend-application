import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GRInterestDetailsFormService, GRInterestDetailsFormGroup } from './gr-interest-details-form.service';
import { IGRInterestDetails } from '../gr-interest-details.model';
import { GRInterestDetailsService } from '../service/gr-interest-details.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

@Component({
  selector: 'jhi-gr-interest-details-update',
  templateUrl: './gr-interest-details-update.component.html',
})
export class GRInterestDetailsUpdateComponent implements OnInit {
  isSaving = false;
  gRInterestDetails: IGRInterestDetails | null = null;

  societiesSharedCollection: ISociety[] = [];

  editForm: GRInterestDetailsFormGroup = this.gRInterestDetailsFormService.createGRInterestDetailsFormGroup();

  constructor(
    protected gRInterestDetailsService: GRInterestDetailsService,
    protected gRInterestDetailsFormService: GRInterestDetailsFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gRInterestDetails }) => {
      this.gRInterestDetails = gRInterestDetails;
      if (gRInterestDetails) {
        this.updateForm(gRInterestDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gRInterestDetails = this.gRInterestDetailsFormService.getGRInterestDetails(this.editForm);
    if (gRInterestDetails.id !== null) {
      this.subscribeToSaveResponse(this.gRInterestDetailsService.update(gRInterestDetails));
    } else {
      this.subscribeToSaveResponse(this.gRInterestDetailsService.create(gRInterestDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGRInterestDetails>>): void {
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

  protected updateForm(gRInterestDetails: IGRInterestDetails): void {
    this.gRInterestDetails = gRInterestDetails;
    this.gRInterestDetailsFormService.resetForm(this.editForm, gRInterestDetails);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      gRInterestDetails.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.gRInterestDetails?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
