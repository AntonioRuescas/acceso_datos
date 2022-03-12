import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartida } from '../partida.model';
import { PartidaService } from '../service/partida.service';

@Component({
  templateUrl: './partida-delete-dialog.component.html',
})
export class PartidaDeleteDialogComponent {
  partida?: IPartida;

  constructor(protected partidaService: PartidaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partidaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
