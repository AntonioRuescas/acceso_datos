import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IJuego, Juego } from '../juego.model';
import { JuegoService } from '../service/juego.service';

import { JuegoRoutingResolveService } from './juego-routing-resolve.service';

describe('Juego routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: JuegoRoutingResolveService;
  let service: JuegoService;
  let resultJuego: IJuego | undefined;

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
    routingResolveService = TestBed.inject(JuegoRoutingResolveService);
    service = TestBed.inject(JuegoService);
    resultJuego = undefined;
  });

  describe('resolve', () => {
    it('should return IJuego returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJuego = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJuego).toEqual({ id: 123 });
    });

    it('should return new IJuego if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJuego = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultJuego).toEqual(new Juego());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Juego })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJuego = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJuego).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
