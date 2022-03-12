import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPartida, Partida } from '../partida.model';
import { PartidaService } from '../service/partida.service';

import { PartidaRoutingResolveService } from './partida-routing-resolve.service';

describe('Partida routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PartidaRoutingResolveService;
  let service: PartidaService;
  let resultPartida: IPartida | undefined;

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
    routingResolveService = TestBed.inject(PartidaRoutingResolveService);
    service = TestBed.inject(PartidaService);
    resultPartida = undefined;
  });

  describe('resolve', () => {
    it('should return IPartida returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartida = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartida).toEqual({ id: 123 });
    });

    it('should return new IPartida if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartida = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPartida).toEqual(new Partida());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Partida })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartida = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartida).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
