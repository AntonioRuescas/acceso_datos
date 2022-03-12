import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JugadorService } from '../service/jugador.service';
import { IJugador, Jugador } from '../jugador.model';
import { IJuego } from 'app/entities/juego/juego.model';
import { JuegoService } from 'app/entities/juego/service/juego.service';
import { IPartida } from 'app/entities/partida/partida.model';
import { PartidaService } from 'app/entities/partida/service/partida.service';

import { JugadorUpdateComponent } from './jugador-update.component';

describe('Jugador Management Update Component', () => {
  let comp: JugadorUpdateComponent;
  let fixture: ComponentFixture<JugadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jugadorService: JugadorService;
  let juegoService: JuegoService;
  let partidaService: PartidaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JugadorUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(JugadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JugadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jugadorService = TestBed.inject(JugadorService);
    juegoService = TestBed.inject(JuegoService);
    partidaService = TestBed.inject(PartidaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Juego query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const juegos: IJuego[] = [{ id: 90663 }];
      jugador.juegos = juegos;

      const juegoCollection: IJuego[] = [{ id: 41710 }];
      jest.spyOn(juegoService, 'query').mockReturnValue(of(new HttpResponse({ body: juegoCollection })));
      const additionalJuegos = [...juegos];
      const expectedCollection: IJuego[] = [...additionalJuegos, ...juegoCollection];
      jest.spyOn(juegoService, 'addJuegoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(juegoService.query).toHaveBeenCalled();
      expect(juegoService.addJuegoToCollectionIfMissing).toHaveBeenCalledWith(juegoCollection, ...additionalJuegos);
      expect(comp.juegosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Partida query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const partidas: IPartida[] = [{ id: 6623 }];
      jugador.partidas = partidas;

      const partidaCollection: IPartida[] = [{ id: 26707 }];
      jest.spyOn(partidaService, 'query').mockReturnValue(of(new HttpResponse({ body: partidaCollection })));
      const additionalPartidas = [...partidas];
      const expectedCollection: IPartida[] = [...additionalPartidas, ...partidaCollection];
      jest.spyOn(partidaService, 'addPartidaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(partidaService.query).toHaveBeenCalled();
      expect(partidaService.addPartidaToCollectionIfMissing).toHaveBeenCalledWith(partidaCollection, ...additionalPartidas);
      expect(comp.partidasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const jugador: IJugador = { id: 456 };
      const juegos: IJuego = { id: 39172 };
      jugador.juegos = [juegos];
      const partidas: IPartida = { id: 81774 };
      jugador.partidas = [partidas];

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(jugador));
      expect(comp.juegosSharedCollection).toContain(juegos);
      expect(comp.partidasSharedCollection).toContain(partidas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jugador>>();
      const jugador = { id: 123 };
      jest.spyOn(jugadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jugador }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(jugadorService.update).toHaveBeenCalledWith(jugador);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jugador>>();
      const jugador = new Jugador();
      jest.spyOn(jugadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jugador }));
      saveSubject.complete();

      // THEN
      expect(jugadorService.create).toHaveBeenCalledWith(jugador);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jugador>>();
      const jugador = { id: 123 };
      jest.spyOn(jugadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jugadorService.update).toHaveBeenCalledWith(jugador);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackJuegoById', () => {
      it('Should return tracked Juego primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackJuegoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPartidaById', () => {
      it('Should return tracked Partida primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartidaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedJuego', () => {
      it('Should return option if no Juego is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedJuego(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Juego for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedJuego(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Juego is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedJuego(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });

    describe('getSelectedPartida', () => {
      it('Should return option if no Partida is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedPartida(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Partida for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedPartida(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Partida is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedPartida(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
