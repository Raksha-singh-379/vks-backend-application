import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyCropRegistrationFormService } from './society-crop-registration-form.service';
import { SocietyCropRegistrationService } from '../service/society-crop-registration.service';
import { ISocietyCropRegistration } from '../society-crop-registration.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SocietyCropRegistrationUpdateComponent } from './society-crop-registration-update.component';

describe('SocietyCropRegistration Management Update Component', () => {
  let comp: SocietyCropRegistrationUpdateComponent;
  let fixture: ComponentFixture<SocietyCropRegistrationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyCropRegistrationFormService: SocietyCropRegistrationFormService;
  let societyCropRegistrationService: SocietyCropRegistrationService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyCropRegistrationUpdateComponent],
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
      .overrideTemplate(SocietyCropRegistrationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyCropRegistrationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyCropRegistrationFormService = TestBed.inject(SocietyCropRegistrationFormService);
    societyCropRegistrationService = TestBed.inject(SocietyCropRegistrationService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyCropRegistration: ISocietyCropRegistration = { id: 456 };
      const society: ISociety = { id: 21539 };
      societyCropRegistration.society = society;

      const societyCollection: ISociety[] = [{ id: 96476 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyCropRegistration });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyCropRegistration: ISocietyCropRegistration = { id: 456 };
      const society: ISociety = { id: 60335 };
      societyCropRegistration.society = society;

      activatedRoute.data = of({ societyCropRegistration });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.societyCropRegistration).toEqual(societyCropRegistration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyCropRegistration>>();
      const societyCropRegistration = { id: 123 };
      jest.spyOn(societyCropRegistrationFormService, 'getSocietyCropRegistration').mockReturnValue(societyCropRegistration);
      jest.spyOn(societyCropRegistrationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyCropRegistration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyCropRegistration }));
      saveSubject.complete();

      // THEN
      expect(societyCropRegistrationFormService.getSocietyCropRegistration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyCropRegistrationService.update).toHaveBeenCalledWith(expect.objectContaining(societyCropRegistration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyCropRegistration>>();
      const societyCropRegistration = { id: 123 };
      jest.spyOn(societyCropRegistrationFormService, 'getSocietyCropRegistration').mockReturnValue({ id: null });
      jest.spyOn(societyCropRegistrationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyCropRegistration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyCropRegistration }));
      saveSubject.complete();

      // THEN
      expect(societyCropRegistrationFormService.getSocietyCropRegistration).toHaveBeenCalled();
      expect(societyCropRegistrationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyCropRegistration>>();
      const societyCropRegistration = { id: 123 };
      jest.spyOn(societyCropRegistrationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyCropRegistration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyCropRegistrationService.update).toHaveBeenCalled();
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
