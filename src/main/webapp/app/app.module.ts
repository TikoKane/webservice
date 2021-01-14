import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DbfirstexoSharedModule } from 'app/shared/shared.module';
import { DbfirstexoCoreModule } from 'app/core/core.module';
import { DbfirstexoAppRoutingModule } from './app-routing.module';
import { DbfirstexoHomeModule } from './home/home.module';
import { DbfirstexoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DbfirstexoSharedModule,
    DbfirstexoCoreModule,
    DbfirstexoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DbfirstexoEntityModule,
    DbfirstexoAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class DbfirstexoAppModule {}
