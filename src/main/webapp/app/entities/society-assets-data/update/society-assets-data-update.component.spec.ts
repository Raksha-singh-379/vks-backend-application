import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyAssetsDataFormService } from './society-assets-data-form.service';
import { SocietyAssetsDataService } from '../service/society-assets-data.service';
import { ISocietyAssetsData } from '../society-assets-data.model';
import { ISocietyAssets } from 'app/entities/society-assets/society-assets.model';
import { SocietyAssetsService } from 'app/entities/society-assets/service/society-assets.service';

import { SocietyAssetsDataUpdateComponent } from './society-assets-data-update.component';

describe('SocietyAssetsData Management Update Component', () => {
  let comp: SocietyAssetsDataUpdateComponent;
  let fixture: ComponentFixture<SocietyAssetsDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyAssetsDataFormService: SocietyAssetsDataFormService;
  let societyAssetsDataService: SocietyAssetsDataService;
  let societyAssetsService: SocietyAssetsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyAssetsDataUpdateComponent],
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
      .overrideTemplate(SocietyAssetsDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyAssetsDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyAssetsDataFormService = TestBed.inject(SocietyAssetsDataFormService);
    societyAssetsDataService = TestBed.inject(SocietyAssetsDataService);
    societyAssetsService = TestBed.inject(SocietyAssetsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SocietyAssets query and add missing value', () => {
      const societyAssetsData: ISocietyAssetsData = { id: 456 };
      const societyAssets: ISocietyAssets = { id: 86677 };
      societyAssetsData.societyAssets = societyAssets;

      const societyAssetsCollection: ISocietyAssets[] = [{ id: 69515 }];
      jest.spyOn(societyAssetsService, 'query').mockReturnValue(of(new HttpResponse({ body: societyAssetsCollection })));
      const additionalSocietyAssets = [societyAssets];
      const expectedCollection: ISocietyAssets[] = [...additionalSocietyAssets, ...societyAssetsCollection];
      jest.spyOn(societyAssetsService, 'addSocietyAssetsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyAssetsData });
      comp.ngOnInit();

      expect(societyAssetsService.query).toHaveBeenCalled();
      expect(societyAssetsService.addSocietyAssetsToCollectionIfMissing).toHaveBeenCalledWith(
        societyAssetsCollection,
        ...additionalSocietyAssets.map(expect.objectContaining)
      );
      expect(comp.societyAssetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyAssetsData: ISocietyAssetsData = { id: 456 };
      const societyAssets: ISocietyAssets = { id: 41294 };
      societyAssetsData.societyAssets = societyAssets;

      activatedRoute.data = of({ societyAssetsData });
      comp.ngOnInit();

      expect(comp.societyAssetsSharedCollection).toContain(societyAssets);
      expect(comp.societyAssetsData).toEqual(societyAssetsData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssetsData>>();
      const societyAssetsData = { id: 123 };
      jest.spyOn(societyAssetsDataFormService, 'getSocietyAssetsData').mockReturnValue(societyAssetsData);
      jest.spyOn(societyAssetsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssetsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyAssetsData }));
      saveSubject.complete();

      // THEN
      expect(societyAssetsDataFormService.getSocietyAssetsData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyAssetsDataService.update).toHaveBeenCalledWith(expect.objectContaining(societyAssetsData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssetsData>>();
      const societyAssetsData = { id: 123 };
      jest.spyOn(societyAssetsDataFormService, 'getSocietyAssetsData').mockReturnValue({ id: null });
      jest.spyOn(societyAssetsDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssetsData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyAssetsData }));
      saveSubject.complete();

      // THEN
      expect(societyAssetsDataFormService.getSocietyAssetsData).toHaveBeenCalled();
      expect(societyAssetsDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssetsData>>();
      const societyAssetsData = { id: 123 };
      jest.spyOn(societyAssetsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssetsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyAssetsDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSocietyAssets', () => {
      it('Should forward to societyAssetsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(societyAssetsService, 'compareSocietyAssets');
        comp.compareSocietyAssets(entity, entity2);
        expect(societyAssetsService.compareSocietyAssets).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
