import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DbfirstexoSharedModule } from 'app/shared/shared.module';
import { CoronaComponent } from './corona.component';
import { CoronaDetailComponent } from './corona-detail.component';
import { CoronaUpdateComponent } from './corona-update.component';
import { CoronaDeleteDialogComponent } from './corona-delete-dialog.component';
import { coronaRoute } from './corona.route';

@NgModule({
  imports: [DbfirstexoSharedModule, RouterModule.forChild(coronaRoute)],
  declarations: [CoronaComponent, CoronaDetailComponent, CoronaUpdateComponent, CoronaDeleteDialogComponent],
  entryComponents: [CoronaDeleteDialogComponent],
})
export class DbfirstexoCoronaModule {}
