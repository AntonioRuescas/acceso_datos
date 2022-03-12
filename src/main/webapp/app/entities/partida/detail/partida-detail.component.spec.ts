import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartidaDetailComponent } from './partida-detail.component';

describe('Partida Management Detail Component', () => {
  let comp: PartidaDetailComponent;
  let fixture: ComponentFixture<PartidaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartidaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partida: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartidaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartidaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partida on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partida).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
