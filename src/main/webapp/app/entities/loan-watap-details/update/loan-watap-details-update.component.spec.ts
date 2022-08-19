import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanWatapDetailsFormService } from './loan-watap-details-form.service';
import { LoanWatapDetailsService } from '../service/loan-watap-details.service';
import { ILoanWatapDetails } from '../loan-watap-details.model';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { LoanDemandService } from 'app/entities/loan-demand/service/loan-demand.service';

import { LoanWatapDetailsUpdateComponent } from './loan-watap-details-update.component';

describe('LoanWatapDetails Management Update Component', () => {
  let comp: LoanWatapDetailsUpdateComponent;
  let fixture: ComponentFixture<LoanWatapDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanWatapDetailsFormService: LoanWatapDetailsFormService;
  let loanWatapDetailsService: LoanWatapDetailsService;
  let loanDemandService: LoanDemandService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanWatapDetailsUpdateComponent],
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
      .overrideTemplate(LoanWatapDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanWatapDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanWatapDetailsFormService = TestBed.inject(LoanWatapDetailsFormService);
    loanWatapDetailsService = TestBed.inject(LoanWatapDetailsService);
    loanDemandService = TestBed.inject(LoanDemandService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanDemand query and add missing value', () => {
      const loanWatapDetails: ILoanWatapDetails = { id: 456 };
      const loanDemand: ILoanDemand = { id: 19742 };
      loanWatapDetails.loanDemand = loanDemand;

      const loanDemandCollection: ILoanDemand[] = [{ id: 98037 }];
      jest.spyOn(loanDemandService, 'query').mockReturnValue(of(new HttpResponse({ body: loanDemandCollection })));
      const additionalLoanDemands = [loanDemand];
      const expectedCollection: ILoanDemand[] = [...additionalLoanDemands, ...loanDemandCollection];
      jest.spyOn(loanDemandService, 'addLoanDemandToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanWatapDetails });
      comp.ngOnInit();

      expect(loanDemandService.query).toHaveBeenCalled();
      expect(loanDemandService.addLoanDemandToCollectionIfMissing).toHaveBeenCalledWith(
        loanDemandCollection,
        ...additionalLoanDemands.map(expect.objectContaining)
      );
      expect(comp.loanDemandsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanWatapDetails: ILoanWatapDetails = { id: 456 };
      const loanDemand: ILoanDemand = { id: 71838 };
      loanWatapDetails.loanDemand = loanDemand;

      activatedRoute.data = of({ loanWatapDetails });
      comp.ngOnInit();

      expect(comp.loanDemandsSharedCollection).toContain(loanDemand);
      expect(comp.loanWatapDetails).toEqual(loanWatapDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanWatapDetails>>();
      const loanWatapDetails = { id: 123 };
      jest.spyOn(loanWatapDetailsFormService, 'getLoanWatapDetails').mockReturnValue(loanWatapDetails);
      jest.spyOn(loanWatapDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanWatapDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanWatapDetails }));
      saveSubject.complete();

      // THEN
      expect(loanWatapDetailsFormService.getLoanWatapDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanWatapDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(loanWatapDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanWatapDetails>>();
      const loanWatapDetails = { id: 123 };
      jest.spyOn(loanWatapDetailsFormService, 'getLoanWatapDetails').mockReturnValue({ id: null });
      jest.spyOn(loanWatapDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanWatapDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanWatapDetails }));
      saveSubject.complete();

      // THEN
      expect(loanWatapDetailsFormService.getLoanWatapDetails).toHaveBeenCalled();
      expect(loanWatapDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanWatapDetails>>();
      const loanWatapDetails = { id: 123 };
      jest.spyOn(loanWatapDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanWatapDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanWatapDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLoanDemand', () => {
      it('Should forward to loanDemandService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanDemandService, 'compareLoanDemand');
        comp.compareLoanDemand(entity, entity2);
        expect(loanDemandService.compareLoanDemand).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
