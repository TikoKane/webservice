import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICorona, Corona } from 'app/shared/model/corona.model';
import { CoronaService } from './corona.service';

@Component({
  selector: 'jhi-corona-update',
  templateUrl: './corona-update.component.html',
})
export class CoronaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombrecasparjour: [null, [Validators.required]],
    caspositif: [null, [Validators.required]],
    cascommunautaire: [null, [Validators.required]],
    casgrave: [null, [Validators.required]],
    guerison: [],
    deces: [],
    cascontact: [],
    casimporte: [],
  });

  constructor(protected coronaService: CoronaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ corona }) => {
      this.updateForm(corona);
    });
  }

  updateForm(corona: ICorona): void {
    this.editForm.patchValue({
      id: corona.id,
      nombrecasparjour: corona.nombrecasparjour,
      caspositif: corona.caspositif,
      cascommunautaire: corona.cascommunautaire,
      casgrave: corona.casgrave,
      guerison: corona.guerison,
      deces: corona.deces,
      cascontact: corona.cascontact,
      casimporte: corona.casimporte,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const corona = this.createFromForm();
    if (corona.id !== undefined) {
      this.subscribeToSaveResponse(this.coronaService.update(corona));
    } else {
      this.subscribeToSaveResponse(this.coronaService.create(corona));
    }
  }

  private createFromForm(): ICorona {
    return {
      ...new Corona(),
      id: this.editForm.get(['id'])!.value,
      nombrecasparjour: this.editForm.get(['nombrecasparjour'])!.value,
      caspositif: this.editForm.get(['caspositif'])!.value,
      cascommunautaire: this.editForm.get(['cascommunautaire'])!.value,
      casgrave: this.editForm.get(['casgrave'])!.value,
      guerison: this.editForm.get(['guerison'])!.value,
      deces: this.editForm.get(['deces'])!.value,
      cascontact: this.editForm.get(['cascontact'])!.value,
      casimporte: this.editForm.get(['casimporte'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorona>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
