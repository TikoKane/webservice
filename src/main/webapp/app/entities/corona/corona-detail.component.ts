import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICorona } from 'app/shared/model/corona.model';

@Component({
  selector: 'jhi-corona-detail',
  templateUrl: './corona-detail.component.html',
})
export class CoronaDetailComponent implements OnInit {
  corona: ICorona | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ corona }) => (this.corona = corona));
  }

  previousState(): void {
    window.history.back();
  }
}
