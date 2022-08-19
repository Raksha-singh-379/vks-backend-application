import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import { LoanDisbursementDetailsService } from '../service/loan-disbursement-details.service';

import { LoanDisbursementDetailsRoutingResolveService } from './loan-disbursement-details-routing-resolve.service';

describe('LoanDisbursementDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LoanDisbursementDetailsRoutingResolveService;
  let service: LoanDisbursementDetailsService;
  let resultLoanDisbursementDetails: ILoanDisbursementDetails | null | undefined;

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
    routingResolveService = TestBed.inject(LoanDisbursementDetailsRoutingResolveService);
    service = TestBed.inject(LoanDisbursementDetailsService);
    resultLoanDisbursementDetails = undefined;
  });

  describe('resolve', () => {
    it('should return ILoanDisbursementDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoanDisbursementDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLoanDisbursementDetails).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoanDisbursementDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLoanDisbursementDetails).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILoanDisbursementDetails>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoanDisbursementDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLoanDisbursementDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
