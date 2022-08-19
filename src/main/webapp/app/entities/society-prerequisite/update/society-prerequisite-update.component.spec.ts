import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyPrerequisiteFormService } from './society-prerequisite-form.service';
import { SocietyPrerequisiteService } from '../service/society-prerequisite.service';
import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SocietyPrerequisiteUpdateComponent } from './society-prerequisite-update.component';

describe('SocietyPrerequisite Management Update Component', () => {
  let comp: SocietyPrerequisiteUpdateComponent;
  let fixture: ComponentFixture<SocietyPrerequisiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyPrerequisiteFormService: SocietyPrerequisiteFormService;
  let societyPrerequisiteService: SocietyPrerequisiteService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyPrerequisiteUpdateComponent],
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
      .overrideTemplate(SocietyPrerequisiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyPrerequisiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyPrerequisiteFormService = TestBed.inject(SocietyPrerequisiteFormService);
    societyPrerequisiteService = TestBed.inject(SocietyPrerequisiteService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyPrerequisite: ISocietyPrerequisite = { id: 456 };
      const society: ISociety = { id: 99313 };
      societyPrerequisite.society = society;

      const societyCollection: ISociety[] = [{ id: 65927 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyPrerequisite });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyPrerequisite: ISocietyPrerequisite = { id: 456 };
      const society: ISociety = { id: 33271 };
      societyPrerequisite.society = society;

      activatedRoute.data = of({ societyPrerequisite });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.societyPrerequisite).toEqual(societyPrerequisite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyPrerequisite>>();
      const societyPrerequisite = { id: 123 };
      jest.spyOn(societyPrerequisiteFormService, 'getSocietyPrerequisite').mockReturnValue(societyPrerequisite);
      jest.spyOn(societyPrerequisiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyPrerequisite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyPrerequisite }));
      saveSubject.complete();

      // THEN
      expect(societyPrerequisiteFormService.getSocietyPrerequisite).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyPrerequisiteService.update).toHaveBeenCalledWith(expect.objectContaining(societyPrerequisite));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyPrerequisite>>();
      const societyPrerequisite = { id: 123 };
      jest.spyOn(societyPrerequisiteFormService, 'getSocietyPrerequisite').mockReturnValue({ id: null });
      jest.spyOn(societyPrerequisiteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyPrerequisite: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyPrerequisite }));
      saveSubject.complete();

      // THEN
      expect(societyPrerequisiteFormService.getSocietyPrerequisite).toHaveBeenCalled();
      expect(societyPrerequisiteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyPrerequisite>>();
      const societyPrerequisite = { id: 123 };
      jest.spyOn(societyPrerequisiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyPrerequisite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyPrerequisiteService.update).toHaveBeenCalled();
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
