import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyLoanProductDetailComponent } from './society-loan-product-detail.component';

describe('SocietyLoanProduct Management Detail Component', () => {
  let comp: SocietyLoanProductDetailComponent;
  let fixture: ComponentFixture<SocietyLoanProductDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyLoanProductDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyLoanProduct: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyLoanProductDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyLoanProductDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyLoanProduct on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyLoanProduct).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
