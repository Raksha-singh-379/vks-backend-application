import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyFormService } from './society-form.service';
import { SocietyService } from '../service/society.service';
import { ISociety } from '../society.model';
import { IVillage } from 'app/entities/village/village.model';
import { VillageService } from 'app/entities/village/service/village.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';

import { SocietyUpdateComponent } from './society-update.component';

describe('Society Management Update Component', () => {
  let comp: SocietyUpdateComponent;
  let fixture: ComponentFixture<SocietyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyFormService: SocietyFormService;
  let societyService: SocietyService;
  let villageService: VillageService;
  let stateService: StateService;
  let districtService: DistrictService;
  let talukaService: TalukaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SocietyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyFormService = TestBed.inject(SocietyFormService);
    societyService = TestBed.inject(SocietyService);
    villageService = TestBed.inject(VillageService);
    stateService = TestBed.inject(StateService);
    districtService = TestBed.inject(DistrictService);
    talukaService = TestBed.inject(TalukaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Village query and add missing value', () => {
      const society: ISociety = { id: 456 };
      const city: IVillage = { id: 51480 };
      society.city = city;

      const villageCollection: IVillage[] = [{ id: 37663 }];
      jest.spyOn(villageService, 'query').mockReturnValue(of(new HttpResponse({ body: villageCollection })));
      const additionalVillages = [city];
      const expectedCollection: IVillage[] = [...additionalVillages, ...villageCollection];
      jest.spyOn(villageService, 'addVillageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(villageService.query).toHaveBeenCalled();
      expect(villageService.addVillageToCollectionIfMissing).toHaveBeenCalledWith(
        villageCollection,
        ...additionalVillages.map(expect.objectContaining)
      );
      expect(comp.villagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call State query and add missing value', () => {
      const society: ISociety = { id: 456 };
      const state: IState = { id: 34144 };
      society.state = state;

      const stateCollection: IState[] = [{ id: 68311 }];
      jest.spyOn(stateService, 'query').mockReturnValue(of(new HttpResponse({ body: stateCollection })));
      const additionalStates = [state];
      const expectedCollection: IState[] = [...additionalStates, ...stateCollection];
      jest.spyOn(stateService, 'addStateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(stateService.query).toHaveBeenCalled();
      expect(stateService.addStateToCollectionIfMissing).toHaveBeenCalledWith(
        stateCollection,
        ...additionalStates.map(expect.objectContaining)
      );
      expect(comp.statesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call District query and add missing value', () => {
      const society: ISociety = { id: 456 };
      const district: IDistrict = { id: 32015 };
      society.district = district;

      const districtCollection: IDistrict[] = [{ id: 21463 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(
        districtCollection,
        ...additionalDistricts.map(expect.objectContaining)
      );
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Taluka query and add missing value', () => {
      const society: ISociety = { id: 456 };
      const taluka: ITaluka = { id: 14185 };
      society.taluka = taluka;

      const talukaCollection: ITaluka[] = [{ id: 56 }];
      jest.spyOn(talukaService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaCollection })));
      const additionalTalukas = [taluka];
      const expectedCollection: ITaluka[] = [...additionalTalukas, ...talukaCollection];
      jest.spyOn(talukaService, 'addTalukaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(talukaService.query).toHaveBeenCalled();
      expect(talukaService.addTalukaToCollectionIfMissing).toHaveBeenCalledWith(
        talukaCollection,
        ...additionalTalukas.map(expect.objectContaining)
      );
      expect(comp.talukasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Society query and add missing value', () => {
      const society: ISociety = { id: 456 };
      const society: ISociety = { id: 41577 };
      society.society = society;

      const societyCollection: ISociety[] = [{ id: 83249 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const society: ISociety = { id: 456 };
      const city: IVillage = { id: 8986 };
      society.city = city;
      const state: IState = { id: 80278 };
      society.state = state;
      const district: IDistrict = { id: 55230 };
      society.district = district;
      const taluka: ITaluka = { id: 22180 };
      society.taluka = taluka;
      const society: ISociety = { id: 20492 };
      society.society = society;

      activatedRoute.data = of({ society });
      comp.ngOnInit();

      expect(comp.villagesSharedCollection).toContain(city);
      expect(comp.statesSharedCollection).toContain(state);
      expect(comp.districtsSharedCollection).toContain(district);
      expect(comp.talukasSharedCollection).toContain(taluka);
      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.society).toEqual(society);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociety>>();
      const society = { id: 123 };
      jest.spyOn(societyFormService, 'getSociety').mockReturnValue(society);
      jest.spyOn(societyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ society });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: society }));
      saveSubject.complete();

      // THEN
      expect(societyFormService.getSociety).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyService.update).toHaveBeenCalledWith(expect.objectContaining(society));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociety>>();
      const society = { id: 123 };
      jest.spyOn(societyFormService, 'getSociety').mockReturnValue({ id: null });
      jest.spyOn(societyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ society: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: society }));
      saveSubject.complete();

      // THEN
      expect(societyFormService.getSociety).toHaveBeenCalled();
      expect(societyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociety>>();
      const society = { id: 123 };
      jest.spyOn(societyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ society });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareVillage', () => {
      it('Should forward to villageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(villageService, 'compareVillage');
        comp.compareVillage(entity, entity2);
        expect(villageService.compareVillage).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareState', () => {
      it('Should forward to stateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(stateService, 'compareState');
        comp.compareState(entity, entity2);
        expect(stateService.compareState).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDistrict', () => {
      it('Should forward to districtService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(districtService, 'compareDistrict');
        comp.compareDistrict(entity, entity2);
        expect(districtService.compareDistrict).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTaluka', () => {
      it('Should forward to talukaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(talukaService, 'compareTaluka');
        comp.compareTaluka(entity, entity2);
        expect(talukaService.compareTaluka).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSociety', () => {
      it('Should forward to societyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(societyService, 'compareSociety');
        comp.compareSociety(entity, entity2);
        expect(societyService.compareSociety).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
