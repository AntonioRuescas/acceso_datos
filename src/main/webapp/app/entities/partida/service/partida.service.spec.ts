import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPartida, Partida } from '../partida.model';

import { PartidaService } from './partida.service';

describe('Partida Service', () => {
  let service: PartidaService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartida;
  let expectedResult: IPartida | IPartida[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartidaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      ganador: 'AAAAAAA',
      perdedor: 'AAAAAAA',
      puntosDelGanador: 0,
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

    it('should create a Partida', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Partida()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Partida', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ganador: 'BBBBBB',
          perdedor: 'BBBBBB',
          puntosDelGanador: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Partida', () => {
      const patchObject = Object.assign(
        {
          perdedor: 'BBBBBB',
        },
        new Partida()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Partida', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ganador: 'BBBBBB',
          perdedor: 'BBBBBB',
          puntosDelGanador: 1,
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

    it('should delete a Partida', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartidaToCollectionIfMissing', () => {
      it('should add a Partida to an empty array', () => {
        const partida: IPartida = { id: 123 };
        expectedResult = service.addPartidaToCollectionIfMissing([], partida);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partida);
      });

      it('should not add a Partida to an array that contains it', () => {
        const partida: IPartida = { id: 123 };
        const partidaCollection: IPartida[] = [
          {
            ...partida,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartidaToCollectionIfMissing(partidaCollection, partida);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Partida to an array that doesn't contain it", () => {
        const partida: IPartida = { id: 123 };
        const partidaCollection: IPartida[] = [{ id: 456 }];
        expectedResult = service.addPartidaToCollectionIfMissing(partidaCollection, partida);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partida);
      });

      it('should add only unique Partida to an array', () => {
        const partidaArray: IPartida[] = [{ id: 123 }, { id: 456 }, { id: 28909 }];
        const partidaCollection: IPartida[] = [{ id: 123 }];
        expectedResult = service.addPartidaToCollectionIfMissing(partidaCollection, ...partidaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partida: IPartida = { id: 123 };
        const partida2: IPartida = { id: 456 };
        expectedResult = service.addPartidaToCollectionIfMissing([], partida, partida2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partida);
        expect(expectedResult).toContain(partida2);
      });

      it('should accept null and undefined values', () => {
        const partida: IPartida = { id: 123 };
        expectedResult = service.addPartidaToCollectionIfMissing([], null, partida, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partida);
      });

      it('should return initial array if no Partida is added', () => {
        const partidaCollection: IPartida[] = [{ id: 123 }];
        expectedResult = service.addPartidaToCollectionIfMissing(partidaCollection, undefined, null);
        expect(expectedResult).toEqual(partidaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
