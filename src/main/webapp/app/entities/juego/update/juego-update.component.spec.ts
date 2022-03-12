import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JuegoService } from '../service/juego.service';
import { IJuego, Juego } from '../juego.model';

import { JuegoUpdateComponent } from './juego-update.component';

describe('Juego Management Update Component', () => {
  let comp: JuegoUpdateComponent;
  let fixture: ComponentFixture<JuegoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let juegoService: JuegoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JuegoUpdateComponent],
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
      .overrideTemplate(JuegoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JuegoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    juegoService = TestBed.inject(JuegoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const juego: IJuego = { id: 456 };

      activatedRoute.data = of({ juego });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(juego));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Juego>>();
      const juego = { id: 123 };
      jest.spyOn(juegoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ juego });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: juego }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(juegoService.update).toHaveBeenCalledWith(juego);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Juego>>();
      const juego = new Juego();
      jest.spyOn(juegoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ juego });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: juego }));
      saveSubject.complete();

      // THEN
      expect(juegoService.create).toHaveBeenCalledWith(juego);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Juego>>();
      const juego = { id: 123 };
      jest.spyOn(juegoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ juego });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(juegoService.update).toHaveBeenCalledWith(juego);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
