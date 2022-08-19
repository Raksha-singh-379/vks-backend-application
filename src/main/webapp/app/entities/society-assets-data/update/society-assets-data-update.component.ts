import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyAssetsDataFormService, SocietyAssetsDataFormGroup } from './society-assets-data-form.service';
import { ISocietyAssetsData } from '../society-assets-data.model';
import { SocietyAssetsDataService } from '../service/society-assets-data.service';
import { ISocietyAssets } from 'app/entities/society-assets/society-assets.model';
import { SocietyAssetsService } from 'app/entities/society-assets/service/society-assets.service';

@Component({
  selector: 'jhi-society-assets-data-update',
  templateUrl: './society-assets-data-update.component.html',
})
export class SocietyAssetsDataUpdateComponent implements OnInit {
  isSaving = false;
  societyAssetsData: ISocietyAssetsData | null = null;

  societyAssetsSharedCollection: ISocietyAssets[] = [];

  editForm: SocietyAssetsDataFormGroup = this.societyAssetsDataFormService.createSocietyAssetsDataFormGroup();

  constructor(
    protected societyAssetsDataService: SocietyAssetsDataService,
    protected societyAssetsDataFormService: SocietyAssetsDataFormService,
    protected societyAssetsService: SocietyAssetsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSocietyAssets = (o1: ISocietyAssets | null, o2: ISocietyAssets | null): boolean =>
    this.societyAssetsService.compareSocietyAssets(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyAssetsData }) => {
      this.societyAssetsData = societyAssetsData;
      if (societyAssetsData) {
        this.updateForm(societyAssetsData);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyAssetsData = this.societyAssetsDataFormService.getSocietyAssetsData(this.editForm);
    if (societyAssetsData.id !== null) {
      this.subscribeToSaveResponse(this.societyAssetsDataService.update(societyAssetsData));
    } else {
      this.subscribeToSaveResponse(this.societyAssetsDataService.create(societyAssetsData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyAssetsData>>): void {
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

  protected updateForm(societyAssetsData: ISocietyAssetsData): void {
    this.societyAssetsData = societyAssetsData;
    this.societyAssetsDataFormService.resetForm(this.editForm, societyAssetsData);

    this.societyAssetsSharedCollection = this.societyAssetsService.addSocietyAssetsToCollectionIfMissing<ISocietyAssets>(
      this.societyAssetsSharedCollection,
      societyAssetsData.societyAssets
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyAssetsService
      .query()
      .pipe(map((res: HttpResponse<ISocietyAssets[]>) => res.body ?? []))
      .pipe(
        map((societyAssets: ISocietyAssets[]) =>
          this.societyAssetsService.addSocietyAssetsToCollectionIfMissing<ISocietyAssets>(
            societyAssets,
            this.societyAssetsData?.societyAssets
          )
        )
      )
      .subscribe((societyAssets: ISocietyAssets[]) => (this.societyAssetsSharedCollection = societyAssets));
  }
}
