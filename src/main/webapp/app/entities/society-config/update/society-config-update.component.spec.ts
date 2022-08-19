import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocietyConfigFormService } from './society-config-form.service';
import { SocietyConfigService } from '../service/society-config.service';
import { ISocietyConfig } from '../society-config.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';

import { SocietyConfigUpdateComponent } from './society-config-update.component';

describe('SocietyConfig Management Update Component', () => {
  let comp: SocietyConfigUpdateComponent;
  let fixture: ComponentFixture<SocietyConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societyConfigFormService: SocietyConfigFormService;
  let societyConfigService: SocietyConfigService;
  let societyService: SocietyService;
  let bankDhoranDetailsService: BankDhoranDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocietyConfigUpdateComponent],
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
      .overrideTemplate(SocietyConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocietyConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societyConfigFormService = TestBed.inject(SocietyConfigFormService);
    societyConfigService = TestBed.inject(SocietyConfigService);
    societyService = TestBed.inject(SocietyService);
    bankDhoranDetailsService = TestBed.inject(BankDhoranDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const societyConfig: ISocietyConfig = { id: 456 };
      const society: ISociety = { id: 23489 };
      societyConfig.society = society;

      const societyCollection: ISociety[] = [{ id: 90865 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyConfig });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BankDhoranDetails query and add missing value', () => {
      const societyConfig: ISocietyConfig = { id: 456 };
      const bankDhoranDetails: IBankDhoranDetails = { id: 43085 };
      societyConfig.bankDhoranDetails = bankDhoranDetails;

      const bankDhoranDetailsCollection: IBankDhoranDetails[] = [{ id: 80776 }];
      jest.spyOn(bankDhoranDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: bankDhoranDetailsCollection })));
      const additionalBankDhoranDetails = [bankDhoranDetails];
      const expectedCollection: IBankDhoranDetails[] = [...additionalBankDhoranDetails, ...bankDhoranDetailsCollection];
      jest.spyOn(bankDhoranDetailsService, 'addBankDhoranDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ societyConfig });
      comp.ngOnInit();

      expect(bankDhoranDetailsService.query).toHaveBeenCalled();
      expect(bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        bankDhoranDetailsCollection,
        ...additionalBankDhoranDetails.map(expect.objectContaining)
      );
      expect(comp.bankDhoranDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const societyConfig: ISocietyConfig = { id: 456 };
      const society: ISociety = { id: 42156 };
      societyConfig.society = society;
      const bankDhoranDetails: IBankDhoranDetails = { id: 77778 };
      societyConfig.bankDhoranDetails = bankDhoranDetails;

      activatedRoute.data = of({ societyConfig });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.bankDhoranDetailsSharedCollection).toContain(bankDhoranDetails);
      expect(comp.societyConfig).toEqual(societyConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyConfig>>();
      const societyConfig = { id: 123 };
      jest.spyOn(societyConfigFormService, 'getSocietyConfig').mockReturnValue(societyConfig);
      jest.spyOn(societyConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyConfig }));
      saveSubject.complete();

      // THEN
      expect(societyConfigFormService.getSocietyConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societyConfigService.update).toHaveBeenCalledWith(expect.objectContaining(societyConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyConfig>>();
      const societyConfig = { id: 123 };
      jest.spyOn(societyConfigFormService, 'getSocietyConfig').mockReturnValue({ id: null });
      jest.spyOn(societyConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societyConfig }));
      saveSubject.complete();

      // THEN
      expect(societyConfigFormService.getSocietyConfig).toHaveBeenCalled();
      expect(societyConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocietyConfig>>();
      const societyConfig = { id: 123 };
      jest.spyOn(societyConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societyConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societyConfigService.update).toHaveBeenCalled();
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
  });
});
