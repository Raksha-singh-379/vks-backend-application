import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyNpaSettingFormService } from './society-npa-setting-form.service';
import { SocietyNpaSettingService } from '../service/society-npa-setting.service';
import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SocietyNpaSettingUpdateComponent } from './society-npa-setting-update.component';

describe('SocietyNpaSetting Management Update Component', () => {
  let comp: SocietyNpaSettingUpdateComponent;
  let fixture: ComponentFixture<SocietyNpaSettingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyNpaSettingFormService: SocietyNpaSettingFormService;
  let societyNpaSettingService: SocietyNpaSettingService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyNpaSettingUpdateComponent],
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
      .overrideTemplate(SocietyNpaSettingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyNpaSettingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyNpaSettingFormService = TestBed.inject(SocietyNpaSettingFormService);
    societyNpaSettingService = TestBed.inject(SocietyNpaSettingService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyNpaSetting: ISocietyNpaSetting = { id: 456 };
      const society: ISociety = { id: 4006 };
      societyNpaSetting.society = society;

      const societyCollection: ISociety[] = [{ id: 91188 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyNpaSetting });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyNpaSetting: ISocietyNpaSetting = { id: 456 };
      const society: ISociety = { id: 86617 };
      societyNpaSetting.society = society;

      activatedRoute.data = of({ societyNpaSetting });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.societyNpaSetting).toEqual(societyNpaSetting);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyNpaSetting>>();
      const societyNpaSetting = { id: 123 };
      jest.spyOn(societyNpaSettingFormService, 'getSocietyNpaSetting').mockReturnValue(societyNpaSetting);
      jest.spyOn(societyNpaSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyNpaSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyNpaSetting }));
      saveSubject.complete();

      // THEN
      expect(societyNpaSettingFormService.getSocietyNpaSetting).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyNpaSettingService.update).toHaveBeenCalledWith(expect.objectContaining(societyNpaSetting));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyNpaSetting>>();
      const societyNpaSetting = { id: 123 };
      jest.spyOn(societyNpaSettingFormService, 'getSocietyNpaSetting').mockReturnValue({ id: null });
      jest.spyOn(societyNpaSettingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyNpaSetting: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyNpaSetting }));
      saveSubject.complete();

      // THEN
      expect(societyNpaSettingFormService.getSocietyNpaSetting).toHaveBeenCalled();
      expect(societyNpaSettingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyNpaSetting>>();
      const societyNpaSetting = { id: 123 };
      jest.spyOn(societyNpaSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyNpaSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyNpaSettingService.update).toHaveBeenCalled();
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
