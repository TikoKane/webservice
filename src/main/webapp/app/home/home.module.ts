import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DbfirstexoSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [DbfirstexoSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class DbfirstexoHomeModule {}
