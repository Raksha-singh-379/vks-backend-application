import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanDisbursementDetailsDetailComponent } from './loan-disbursement-details-detail.component';

describe('LoanDisbursementDetails Management Detail Component', () => {
  let comp: LoanDisbursementDetailsDetailComponent;
  let fixture: ComponentFixture<LoanDisbursementDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanDisbursementDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanDisbursementDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanDisbursementDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanDisbursementDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanDisbursementDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanDisbursementDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
