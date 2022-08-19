import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GRInterestDetailsFormService } from './gr-interest-details-form.service';
import { GRInterestDetailsService } from '../service/gr-interest-details.service';
import { IGRInterestDetails } from '../gr-interest-details.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { GRInterestDetailsUpdateComponent } from './gr-interest-details-update.component';

describe('GRInterestDetails Management Update Component', () => {
  let comp: GRInterestDetailsUpdateComponent;
  let fixture: ComponentFixture<GRInterestDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gRInterestDetailsFormService: GRInterestDetailsFormService;
  let gRInterestDetailsService: GRInterestDetailsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GRInterestDetailsUpdateComponent],
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
      .overrideTemplate(GRInterestDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GRInterestDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gRInterestDetailsFormService = TestBed.inject(GRInterestDetailsFormService);
    gRInterestDetailsService = TestBed.inject(GRInterestDetailsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const gRInterestDetails: IGRInterestDetails = { id: 456 };
      const society: ISociety = { id: 76354 };
      gRInterestDetails.society = society;

      const societyCollection: ISociety[] = [{ id: 10680 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gRInterestDetails });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const gRInterestDetails: IGRInterestDetails = { id: 456 };
      const society: ISociety = { id: 37969 };
      gRInterestDetails.society = society;

      activatedRoute.data = of({ gRInterestDetails });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.gRInterestDetails).toEqual(gRInterestDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGRInterestDetails>>();
      const gRInterestDetails = { id: 123 };
      jest.spyOn(gRInterestDetailsFormService, 'getGRInterestDetails').mockReturnValue(gRInterestDetails);
      jest.spyOn(gRInterestDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gRInterestDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gRInterestDetails }));
      saveSubject.complete();

      // THEN
      expect(gRInterestDetailsFormService.getGRInterestDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gRInterestDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(gRInterestDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGRInterestDetails>>();
      const gRInterestDetails = { id: 123 };
      jest.spyOn(gRInterestDetailsFormService, 'getGRInterestDetails').mockReturnValue({ id: null });
      jest.spyOn(gRInterestDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gRInterestDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gRInterestDetails }));
      saveSubject.complete();

      // THEN
      expect(gRInterestDetailsFormService.getGRInterestDetails).toHaveBeenCalled();
      expect(gRInterestDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGRInterestDetails>>();
      const gRInterestDetails = { id: 123 };
      jest.spyOn(gRInterestDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gRInterestDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gRInterestDetailsService.update).toHaveBeenCalled();
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
