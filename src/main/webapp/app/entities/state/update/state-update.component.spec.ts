import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StateFormService } from './state-form.service';
import { StateService } from '../service/state.service';
import { IState } from '../state.model';

import { StateUpdateComponent } from './state-update.component';

describe('State Management Update Component', () => {
  let comp: StateUpdateComponent;
  let fixture: ComponentFixture<StateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stateFormService: StateFormService;
  let stateService: StateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StateUpdateComponent],
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
      .overrideTemplate(StateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stateFormService = TestBed.inject(StateFormService);
    stateService = TestBed.inject(StateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const state: IState = { id: 456 };

      activatedRoute.data = of({ state });
      comp.ngOnInit();

      expect(comp.state).toEqual(state);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IState>>();
      const state = { id: 123 };
      jest.spyOn(stateFormService, 'getState').mockReturnValue(state);
      jest.spyOn(stateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ state });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: state }));
      saveSubject.complete();

      // THEN
      expect(stateFormService.getState).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stateService.update).toHaveBeenCalledWith(expect.objectContaining(state));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IState>>();
      const state = { id: 123 };
      jest.spyOn(stateFormService, 'getState').mockReturnValue({ id: null });
      jest.spyOn(stateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ state: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: state }));
      saveSubject.complete();

      // THEN
      expect(stateFormService.getState).toHaveBeenCalled();
      expect(stateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IState>>();
      const state = { id: 123 };
      jest.spyOn(stateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ state });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
