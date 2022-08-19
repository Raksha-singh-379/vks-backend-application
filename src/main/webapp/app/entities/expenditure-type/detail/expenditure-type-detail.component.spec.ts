import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExpenditureTypeDetailComponent } from './expenditure-type-detail.component';

describe('ExpenditureType Management Detail Component', () => {
  let comp: ExpenditureTypeDetailComponent;
  let fixture: ComponentFixture<ExpenditureTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExpenditureTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ expenditureType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExpenditureTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExpenditureTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load expenditureType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.expenditureType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
