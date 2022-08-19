import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyBanksDetailsFormService } from './society-banks-details-form.service';
import { SocietyBanksDetailsService } from '../service/society-banks-details.service';
import { ISocietyBanksDetails } from '../society-banks-details.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { SocietyBanksDetailsUpdateComponent } from './society-banks-details-update.component';

describe('SocietyBanksDetails Management Update Component', () => {
  let comp: SocietyBanksDetailsUpdateComponent;
  let fixture: ComponentFixture<SocietyBanksDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyBanksDetailsFormService: SocietyBanksDetailsFormService;
  let societyBanksDetailsService: SocietyBanksDetailsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyBanksDetailsUpdateComponent],
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
      .overrideTemplate(SocietyBanksDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyBanksDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyBanksDetailsFormService = TestBed.inject(SocietyBanksDetailsFormService);
    societyBanksDetailsService = TestBed.inject(SocietyBanksDetailsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyBanksDetails: ISocietyBanksDetails = { id: 456 };
      const society: ISociety = { id: 54802 };
      societyBanksDetails.society = society;

      const societyCollection: ISociety[] = [{ id: 67420 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyBanksDetails });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyBanksDetails: ISocietyBanksDetails = { id: 456 };
      const society: ISociety = { id: 25415 };
      societyBanksDetails.society = society;

      activatedRoute.data = of({ societyBanksDetails });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.societyBanksDetails).toEqual(societyBanksDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyBanksDetails>>();
      const societyBanksDetails = { id: 123 };
      jest.spyOn(societyBanksDetailsFormService, 'getSocietyBanksDetails').mockReturnValue(societyBanksDetails);
      jest.spyOn(societyBanksDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyBanksDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyBanksDetails }));
      saveSubject.complete();

      // THEN
      expect(societyBanksDetailsFormService.getSocietyBanksDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyBanksDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(societyBanksDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyBanksDetails>>();
      const societyBanksDetails = { id: 123 };
      jest.spyOn(societyBanksDetailsFormService, 'getSocietyBanksDetails').mockReturnValue({ id: null });
      jest.spyOn(societyBanksDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyBanksDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyBanksDetails }));
      saveSubject.complete();

      // THEN
      expect(societyBanksDetailsFormService.getSocietyBanksDetails).toHaveBeenCalled();
      expect(societyBanksDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyBanksDetails>>();
      const societyBanksDetails = { id: 123 };
      jest.spyOn(societyBanksDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyBanksDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyBanksDetailsService.update).toHaveBeenCalled();
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
