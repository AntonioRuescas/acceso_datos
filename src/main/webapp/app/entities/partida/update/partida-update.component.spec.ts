import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartidaService } from '../service/partida.service';
import { IPartida, Partida } from '../partida.model';
import { IJuego } from 'app/entities/juego/juego.model';
import { JuegoService } from 'app/entities/juego/service/juego.service';

import { PartidaUpdateComponent } from './partida-update.component';

describe('Partida Management Update Component', () => {
  let comp: PartidaUpdateComponent;
  let fixture: ComponentFixture<PartidaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partidaService: PartidaService;
  let juegoService: JuegoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartidaUpdateComponent],
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
      .overrideTemplate(PartidaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartidaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partidaService = TestBed.inject(PartidaService);
    juegoService = TestBed.inject(JuegoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Juego query and add missing value', () => {
      const partida: IPartida = { id: 456 };
      const juego: IJuego = { id: 28385 };
      partida.juego = juego;

      const juegoCollection: IJuego[] = [{ id: 18654 }];
      jest.spyOn(juegoService, 'query').mockReturnValue(of(new HttpResponse({ body: juegoCollection })));
      const additionalJuegos = [juego];
      const expectedCollection: IJuego[] = [...additionalJuegos, ...juegoCollection];
      jest.spyOn(juegoService, 'addJuegoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      expect(juegoService.query).toHaveBeenCalled();
      expect(juegoService.addJuegoToCollectionIfMissing).toHaveBeenCalledWith(juegoCollection, ...additionalJuegos);
      expect(comp.juegosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partida: IPartida = { id: 456 };
      const juego: IJuego = { id: 68512 };
      partida.juego = juego;

      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partida));
      expect(comp.juegosSharedCollection).toContain(juego);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = { id: 123 };
      jest.spyOn(partidaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partida }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partidaService.update).toHaveBeenCalledWith(partida);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = new Partida();
      jest.spyOn(partidaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partida }));
      saveSubject.complete();

      // THEN
      expect(partidaService.create).toHaveBeenCalledWith(partida);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = { id: 123 };
      jest.spyOn(partidaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partidaService.update).toHaveBeenCalledWith(partida);
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
  });
});
