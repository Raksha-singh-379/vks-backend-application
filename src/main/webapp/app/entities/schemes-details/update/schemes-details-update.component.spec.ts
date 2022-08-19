import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SchemesDetailsFormService } from './schemes-details-form.service';
import { SchemesDetailsService } from '../service/schemes-details.service';
import { ISchemesDetails } from '../schemes-details.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SchemesDetailsUpdateComponent } from './schemes-details-update.component';

describe('SchemesDetails Management Update Component', () => {
  let comp: SchemesDetailsUpdateComponent;
  let fixture: ComponentFixture<SchemesDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let schemesDetailsFormService: SchemesDetailsFormService;
  let schemesDetailsService: SchemesDetailsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SchemesDetailsUpdateComponent],
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
      .overrideTemplate(SchemesDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SchemesDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    schemesDetailsFormService = TestBed.inject(SchemesDetailsFormService);
    schemesDetailsService = TestBed.inject(SchemesDetailsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const schemesDetails: ISchemesDetails = { id: 456 };
      const society: ISociety = { id: 7229 };
      schemesDetails.society = society;

      const societyCollection: ISociety[] = [{ id: 18791 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schemesDetails });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const schemesDetails: ISchemesDetails = { id: 456 };
      const society: ISociety = { id: 76972 };
      schemesDetails.society = society;

      activatedRoute.data = of({ schemesDetails });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.schemesDetails).toEqual(schemesDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISchemesDetails>>();
      const schemesDetails = { id: 123 };
      jest.spyOn(schemesDetailsFormService, 'getSchemesDetails').mockReturnValue(schemesDetails);
      jest.spyOn(schemesDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schemesDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schemesDetails }));
      saveSubject.complete();

      // THEN
      expect(schemesDetailsFormService.getSchemesDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(schemesDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(schemesDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISchemesDetails>>();
      const schemesDetails = { id: 123 };
      jest.spyOn(schemesDetailsFormService, 'getSchemesDetails').mockReturnValue({ id: null });
      jest.spyOn(schemesDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schemesDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schemesDetails }));
      saveSubject.complete();

      // THEN
      expect(schemesDetailsFormService.getSchemesDetails).toHaveBeenCalled();
      expect(schemesDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISchemesDetails>>();
      const schemesDetails = { id: 123 };
      jest.spyOn(schemesDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schemesDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(schemesDetailsService.update).toHaveBeenCalled();
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
