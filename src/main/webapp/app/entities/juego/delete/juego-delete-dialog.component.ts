import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJuego } from '../juego.model';
import { JuegoService } from '../service/juego.service';

@Component({
  templateUrl: './juego-delete-dialog.component.html',
})
export class JuegoDeleteDialogComponent {
  juego?: IJuego;

  constructor(protected juegoService: JuegoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.juegoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
