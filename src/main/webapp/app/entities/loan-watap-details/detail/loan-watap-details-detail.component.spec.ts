import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanWatapDetailsDetailComponent } from './loan-watap-details-detail.component';

describe('LoanWatapDetails Management Detail Component', () => {
  let comp: LoanWatapDetailsDetailComponent;
  let fixture: ComponentFixture<LoanWatapDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanWatapDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanWatapDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanWatapDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanWatapDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanWatapDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanWatapDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
