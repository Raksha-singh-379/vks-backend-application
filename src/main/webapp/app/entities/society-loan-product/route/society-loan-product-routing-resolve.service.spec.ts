import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISocietyLoanProduct } from '../society-loan-product.model';
import { SocietyLoanProductService } from '../service/society-loan-product.service';

import { SocietyLoanProductRoutingResolveService } from './society-loan-product-routing-resolve.service';

describe('SocietyLoanProduct routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SocietyLoanProductRoutingResolveService;
  let service: SocietyLoanProductService;
  let resultSocietyLoanProduct: ISocietyLoanProduct | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SocietyLoanProductRoutingResolveService);
    service = TestBed.inject(SocietyLoanProductService);
    resultSocietyLoanProduct = undefined;
  });

  describe('resolve', () => {
    it('should return ISocietyLoanProduct returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyLoanProduct = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyLoanProduct).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyLoanProduct = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSocietyLoanProduct).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISocietyLoanProduct>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyLoanProduct = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyLoanProduct).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
