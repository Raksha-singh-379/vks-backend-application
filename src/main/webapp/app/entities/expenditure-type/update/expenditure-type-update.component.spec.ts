import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExpenditureTypeFormService } from './expenditure-type-form.service';
import { ExpenditureTypeService } from '../service/expenditure-type.service';
import { IExpenditureType } from '../expenditure-type.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { ExpenditureTypeUpdateComponent } from './expenditure-type-update.component';

describe('ExpenditureType Management Update Component', () => {
  let comp: ExpenditureTypeUpdateComponent;
  let fixture: ComponentFixture<ExpenditureTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let expenditureTypeFormService: ExpenditureTypeFormService;
  let expenditureTypeService: ExpenditureTypeService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExpenditureTypeUpdateComponent],
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
      .overrideTemplate(ExpenditureTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExpenditureTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    expenditureTypeFormService = TestBed.inject(ExpenditureTypeFormService);
    expenditureTypeService = TestBed.inject(ExpenditureTypeService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const expenditureType: IExpenditureType = { id: 456 };
      const society: ISociety = { id: 79411 };
      expenditureType.society = society;

      const societyCollection: ISociety[] = [{ id: 20286 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ expenditureType });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const expenditureType: IExpenditureType = { id: 456 };
      const society: ISociety = { id: 98762 };
      expenditureType.society = society;

      activatedRoute.data = of({ expenditureType });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.expenditureType).toEqual(expenditureType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExpenditureType>>();
      const expenditureType = { id: 123 };
      jest.spyOn(expenditureTypeFormService, 'getExpenditureType').mockReturnValue(expenditureType);
      jest.spyOn(expenditureTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ expenditureType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: expenditureType }));
      saveSubject.complete();

      // THEN
      expect(expenditureTypeFormService.getExpenditureType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(expenditureTypeService.update).toHaveBeenCalledWith(expect.objectContaining(expenditureType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExpenditureType>>();
      const expenditureType = { id: 123 };
      jest.spyOn(expenditureTypeFormService, 'getExpenditureType').mockReturnValue({ id: null });
      jest.spyOn(expenditureTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ expenditureType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: expenditureType }));
      saveSubject.complete();

      // THEN
      expect(expenditureTypeFormService.getExpenditureType).toHaveBeenCalled();
      expect(expenditureTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExpenditureType>>();
      const expenditureType = { id: 123 };
      jest.spyOn(expenditureTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ expenditureType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(expenditureTypeService.update).toHaveBeenCalled();
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
