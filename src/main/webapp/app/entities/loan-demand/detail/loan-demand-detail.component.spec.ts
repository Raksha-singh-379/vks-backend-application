import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanDemandDetailComponent } from './loan-demand-detail.component';

describe('LoanDemand Management Detail Component', () => {
  let comp: LoanDemandDetailComponent;
  let fixture: ComponentFixture<LoanDemandDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanDemandDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanDemand: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanDemandDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanDemandDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanDemand on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanDemand).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
