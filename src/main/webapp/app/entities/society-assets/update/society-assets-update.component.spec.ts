import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyAssetsFormService } from './society-assets-form.service';
import { SocietyAssetsService } from '../service/society-assets.service';
import { ISocietyAssets } from '../society-assets.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SocietyAssetsUpdateComponent } from './society-assets-update.component';

describe('SocietyAssets Management Update Component', () => {
  let comp: SocietyAssetsUpdateComponent;
  let fixture: ComponentFixture<SocietyAssetsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyAssetsFormService: SocietyAssetsFormService;
  let societyAssetsService: SocietyAssetsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyAssetsUpdateComponent],
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
      .overrideTemplate(SocietyAssetsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyAssetsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyAssetsFormService = TestBed.inject(SocietyAssetsFormService);
    societyAssetsService = TestBed.inject(SocietyAssetsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyAssets: ISocietyAssets = { id: 456 };
      const society: ISociety = { id: 78215 };
      societyAssets.society = society;

      const societyCollection: ISociety[] = [{ id: 69888 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyAssets });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyAssets: ISocietyAssets = { id: 456 };
      const society: ISociety = { id: 3180 };
      societyAssets.society = society;

      activatedRoute.data = of({ societyAssets });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.societyAssets).toEqual(societyAssets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssets>>();
      const societyAssets = { id: 123 };
      jest.spyOn(societyAssetsFormService, 'getSocietyAssets').mockReturnValue(societyAssets);
      jest.spyOn(societyAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyAssets }));
      saveSubject.complete();

      // THEN
      expect(societyAssetsFormService.getSocietyAssets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyAssetsService.update).toHaveBeenCalledWith(expect.objectContaining(societyAssets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssets>>();
      const societyAssets = { id: 123 };
      jest.spyOn(societyAssetsFormService, 'getSocietyAssets').mockReturnValue({ id: null });
      jest.spyOn(societyAssetsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyAssets }));
      saveSubject.complete();

      // THEN
      expect(societyAssetsFormService.getSocietyAssets).toHaveBeenCalled();
      expect(societyAssetsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyAssets>>();
      const societyAssets = { id: 123 };
      jest.spyOn(societyAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyAssetsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
