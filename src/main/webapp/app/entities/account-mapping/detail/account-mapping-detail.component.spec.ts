import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountMappingDetailComponent } from './account-mapping-detail.component';

describe('AccountMapping Management Detail Component', () => {
  let comp: AccountMappingDetailComponent;
  let fixture: ComponentFixture<AccountMappingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccountMappingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accountMapping: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccountMappingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccountMappingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accountMapping on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accountMapping).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
