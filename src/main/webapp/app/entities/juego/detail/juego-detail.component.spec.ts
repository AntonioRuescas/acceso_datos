import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JuegoDetailComponent } from './juego-detail.component';

describe('Juego Management Detail Component', () => {
  let comp: JuegoDetailComponent;
  let fixture: ComponentFixture<JuegoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JuegoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ juego: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JuegoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JuegoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load juego on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.juego).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
