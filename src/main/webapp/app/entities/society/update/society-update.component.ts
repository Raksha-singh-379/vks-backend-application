import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyFormService, SocietyFormGroup } from './society-form.service';
import { ISociety } from '../society.model';
import { SocietyService } from '../service/society.service';
import { IVillage } from 'app/entities/village/village.model';
import { VillageService } from 'app/entities/village/service/village.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';

@Component({
  selector: 'jhi-society-update',
  templateUrl: './society-update.component.html',
})
export class SocietyUpdateComponent implements OnInit {
  isSaving = false;
  society: ISociety | null = null;

  villagesSharedCollection: IVillage[] = [];
  statesSharedCollection: IState[] = [];
  districtsSharedCollection: IDistrict[] = [];
  talukasSharedCollection: ITaluka[] = [];
  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyFormGroup = this.societyFormService.createSocietyFormGroup();

  constructor(
    protected societyService: SocietyService,
    protected societyFormService: SocietyFormService,
    protected villageService: VillageService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareVillage = (o1: IVillage | null, o2: IVillage | null): boolean => this.villageService.compareVillage(o1, o2);

  compareState = (o1: IState | null, o2: IState | null): boolean => this.stateService.compareState(o1, o2);

  compareDistrict = (o1: IDistrict | null, o2: IDistrict | null): boolean => this.districtService.compareDistrict(o1, o2);

  compareTaluka = (o1: ITaluka | null, o2: ITaluka | null): boolean => this.talukaService.compareTaluka(o1, o2);

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ society }) => {
      this.society = society;
      if (society) {
        this.updateForm(society);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const society = this.societyFormService.getSociety(this.editForm);
    if (society.id !== null) {
      this.subscribeToSaveResponse(this.societyService.update(society));
    } else {
      this.subscribeToSaveResponse(this.societyService.create(society));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISociety>>): void {
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

  protected updateForm(society: ISociety): void {
    this.society = society;
    this.societyFormService.resetForm(this.editForm, society);

    this.villagesSharedCollection = this.villageService.addVillageToCollectionIfMissing<IVillage>(
      this.villagesSharedCollection,
      society.city
    );
    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing<IState>(this.statesSharedCollection, society.state);
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing<IDistrict>(
      this.districtsSharedCollection,
      society.district
    );
    this.talukasSharedCollection = this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(this.talukasSharedCollection, society.taluka);
    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      society.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.villageService
      .query()
      .pipe(map((res: HttpResponse<IVillage[]>) => res.body ?? []))
      .pipe(map((villages: IVillage[]) => this.villageService.addVillageToCollectionIfMissing<IVillage>(villages, this.society?.city)))
      .subscribe((villages: IVillage[]) => (this.villagesSharedCollection = villages));

    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing<IState>(states, this.society?.state)))
      .subscribe((states: IState[]) => (this.statesSharedCollection = states));

    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) => this.districtService.addDistrictToCollectionIfMissing<IDistrict>(districts, this.society?.district))
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));

    this.talukaService
      .query()
      .pipe(map((res: HttpResponse<ITaluka[]>) => res.body ?? []))
      .pipe(map((talukas: ITaluka[]) => this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(talukas, this.society?.taluka)))
      .subscribe((talukas: ITaluka[]) => (this.talukasSharedCollection = talukas));

    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(map((societies: ISociety[]) => this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.society?.society)))
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
