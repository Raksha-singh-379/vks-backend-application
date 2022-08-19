import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankDhoranDetailsDetailComponent } from './bank-dhoran-details-detail.component';

describe('BankDhoranDetails Management Detail Component', () => {
  let comp: BankDhoranDetailsDetailComponent;
  let fixture: ComponentFixture<BankDhoranDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankDhoranDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankDhoranDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BankDhoranDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankDhoranDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankDhoranDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankDhoranDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
