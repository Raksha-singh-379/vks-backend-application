import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyLoanProductFormService } from './society-loan-product-form.service';
import { SocietyLoanProductService } from '../service/society-loan-product.service';
import { ISocietyLoanProduct } from '../society-loan-product.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';
import { IGRInterestDetails } from 'app/entities/gr-interest-details/gr-interest-details.model';
import { GRInterestDetailsService } from 'app/entities/gr-interest-details/service/gr-interest-details.service';

import { SocietyLoanProductUpdateComponent } from './society-loan-product-update.component';

describe('SocietyLoanProduct Management Update Component', () => {
  let comp: SocietyLoanProductUpdateComponent;
  let fixture: ComponentFixture<SocietyLoanProductUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyLoanProductFormService: SocietyLoanProductFormService;
  let societyLoanProductService: SocietyLoanProductService;
  let societyService: SocietyService;
  let bankDhoranDetailsService: BankDhoranDetailsService;
  let gRInterestDetailsService: GRInterestDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyLoanProductUpdateComponent],
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
      .overrideTemplate(SocietyLoanProductUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyLoanProductUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyLoanProductFormService = TestBed.inject(SocietyLoanProductFormService);
    societyLoanProductService = TestBed.inject(SocietyLoanProductService);
    societyService = TestBed.inject(SocietyService);
    bankDhoranDetailsService = TestBed.inject(BankDhoranDetailsService);
    gRInterestDetailsService = TestBed.inject(GRInterestDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyLoanProduct: ISocietyLoanProduct = { id: 456 };
      const society: ISociety = { id: 68422 };
      societyLoanProduct.society = society;

      const societyCollection: ISociety[] = [{ id: 31625 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BankDhoranDetails query and add missing value', () => {
      const societyLoanProduct: ISocietyLoanProduct = { id: 456 };
      const bankDhoranDetails: IBankDhoranDetails = { id: 73757 };
      societyLoanProduct.bankDhoranDetails = bankDhoranDetails;

      const bankDhoranDetailsCollection: IBankDhoranDetails[] = [{ id: 6498 }];
      jest.spyOn(bankDhoranDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: bankDhoranDetailsCollection })));
      const additionalBankDhoranDetails = [bankDhoranDetails];
      const expectedCollection: IBankDhoranDetails[] = [...additionalBankDhoranDetails, ...bankDhoranDetailsCollection];
      jest.spyOn(bankDhoranDetailsService, 'addBankDhoranDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      expect(bankDhoranDetailsService.query).toHaveBeenCalled();
      expect(bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        bankDhoranDetailsCollection,
        ...additionalBankDhoranDetails.map(expect.objectContaining)
      );
      expect(comp.bankDhoranDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GRInterestDetails query and add missing value', () => {
      const societyLoanProduct: ISocietyLoanProduct = { id: 456 };
      const gRInterestDetails: IGRInterestDetails = { id: 20780 };
      societyLoanProduct.gRInterestDetails = gRInterestDetails;

      const gRInterestDetailsCollection: IGRInterestDetails[] = [{ id: 66372 }];
      jest.spyOn(gRInterestDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: gRInterestDetailsCollection })));
      const additionalGRInterestDetails = [gRInterestDetails];
      const expectedCollection: IGRInterestDetails[] = [...additionalGRInterestDetails, ...gRInterestDetailsCollection];
      jest.spyOn(gRInterestDetailsService, 'addGRInterestDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      expect(gRInterestDetailsService.query).toHaveBeenCalled();
      expect(gRInterestDetailsService.addGRInterestDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        gRInterestDetailsCollection,
        ...additionalGRInterestDetails.map(expect.objectContaining)
      );
      expect(comp.gRInterestDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyLoanProduct: ISocietyLoanProduct = { id: 456 };
      const society: ISociety = { id: 53528 };
      societyLoanProduct.society = society;
      const bankDhoranDetails: IBankDhoranDetails = { id: 51916 };
      societyLoanProduct.bankDhoranDetails = bankDhoranDetails;
      const gRInterestDetails: IGRInterestDetails = { id: 38901 };
      societyLoanProduct.gRInterestDetails = gRInterestDetails;

      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.bankDhoranDetailsSharedCollection).toContain(bankDhoranDetails);
      expect(comp.gRInterestDetailsSharedCollection).toContain(gRInterestDetails);
      expect(comp.societyLoanProduct).toEqual(societyLoanProduct);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyLoanProduct>>();
      const societyLoanProduct = { id: 123 };
      jest.spyOn(societyLoanProductFormService, 'getSocietyLoanProduct').mockReturnValue(societyLoanProduct);
      jest.spyOn(societyLoanProductService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyLoanProduct }));
      saveSubject.complete();

      // THEN
      expect(societyLoanProductFormService.getSocietyLoanProduct).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyLoanProductService.update).toHaveBeenCalledWith(expect.objectContaining(societyLoanProduct));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyLoanProduct>>();
      const societyLoanProduct = { id: 123 };
      jest.spyOn(societyLoanProductFormService, 'getSocietyLoanProduct').mockReturnValue({ id: null });
      jest.spyOn(societyLoanProductService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyLoanProduct: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyLoanProduct }));
      saveSubject.complete();

      // THEN
      expect(societyLoanProductFormService.getSocietyLoanProduct).toHaveBeenCalled();
      expect(societyLoanProductService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyLoanProduct>>();
      const societyLoanProduct = { id: 123 };
      jest.spyOn(societyLoanProductService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyLoanProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyLoanProductService.update).toHaveBeenCalled();
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

    describe('compareBankDhoranDetails', () => {
      it('Should forward to bankDhoranDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bankDhoranDetailsService, 'compareBankDhoranDetails');
        comp.compareBankDhoranDetails(entity, entity2);
        expect(bankDhoranDetailsService.compareBankDhoranDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGRInterestDetails', () => {
      it('Should forward to gRInterestDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gRInterestDetailsService, 'compareGRInterestDetails');
        comp.compareGRInterestDetails(entity, entity2);
        expect(gRInterestDetailsService.compareGRInterestDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
