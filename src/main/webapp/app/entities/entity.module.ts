import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'covid',
        loadChildren: () => import('./covid/covid.module').then(m => m.DbfirstexoCovidModule),
      },
      {
        path: 'corona',
        loadChildren: () => import('./corona/corona.module').then(m => m.DbfirstexoCoronaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DbfirstexoEntityModule {}
