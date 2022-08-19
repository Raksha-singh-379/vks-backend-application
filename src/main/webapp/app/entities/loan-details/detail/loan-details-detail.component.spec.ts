import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanDetailsDetailComponent } from './loan-details-detail.component';

describe('LoanDetails Management Detail Component', () => {
  let comp: LoanDetailsDetailComponent;
  let fixture: ComponentFixture<LoanDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
