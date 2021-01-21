import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICorona } from 'app/shared/model/corona.model';
import { CoronaService } from './corona.service';

@Component({
  templateUrl: './corona-delete-dialog.component.html',
})
export class CoronaDeleteDialogComponent {
  corona?: ICorona;

  constructor(protected coronaService: CoronaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.coronaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('coronaListModification');
      this.activeModal.close();
    });
  }
}
