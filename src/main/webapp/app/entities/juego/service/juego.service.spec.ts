import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJuego, Juego } from '../juego.model';

import { JuegoService } from './juego.service';

describe('Juego Service', () => {
  let service: JuegoService;
  let httpMock: HttpTestingController;
  let elemDefault: IJuego;
  let expectedResult: IJuego | IJuego[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JuegoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nombre: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Juego', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Juego()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Juego', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Juego', () => {
      const patchObject = Object.assign(
        {
          nombre: 'BBBBBB',
        },
        new Juego()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Juego', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Juego', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addJuegoToCollectionIfMissing', () => {
      it('should add a Juego to an empty array', () => {
        const juego: IJuego = { id: 123 };
        expectedResult = service.addJuegoToCollectionIfMissing([], juego);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(juego);
      });

      it('should not add a Juego to an array that contains it', () => {
        const juego: IJuego = { id: 123 };
        const juegoCollection: IJuego[] = [
          {
            ...juego,
          },
          { id: 456 },
        ];
        expectedResult = service.addJuegoToCollectionIfMissing(juegoCollection, juego);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Juego to an array that doesn't contain it", () => {
        const juego: IJuego = { id: 123 };
        const juegoCollection: IJuego[] = [{ id: 456 }];
        expectedResult = service.addJuegoToCollectionIfMissing(juegoCollection, juego);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(juego);
      });

      it('should add only unique Juego to an array', () => {
        const juegoArray: IJuego[] = [{ id: 123 }, { id: 456 }, { id: 2075 }];
        const juegoCollection: IJuego[] = [{ id: 123 }];
        expectedResult = service.addJuegoToCollectionIfMissing(juegoCollection, ...juegoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const juego: IJuego = { id: 123 };
        const juego2: IJuego = { id: 456 };
        expectedResult = service.addJuegoToCollectionIfMissing([], juego, juego2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(juego);
        expect(expectedResult).toContain(juego2);
      });

      it('should accept null and undefined values', () => {
        const juego: IJuego = { id: 123 };
        expectedResult = service.addJuegoToCollectionIfMissing([], null, juego, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(juego);
      });

      it('should return initial array if no Juego is added', () => {
        const juegoCollection: IJuego[] = [{ id: 123 }];
        expectedResult = service.addJuegoToCollectionIfMissing(juegoCollection, undefined, null);
        expect(expectedResult).toEqual(juegoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
