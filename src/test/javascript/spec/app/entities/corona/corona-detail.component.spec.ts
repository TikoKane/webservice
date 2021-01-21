import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DbfirstexoTestModule } from '../../../test.module';
import { CoronaDetailComponent } from 'app/entities/corona/corona-detail.component';
import { Corona } from 'app/shared/model/corona.model';

describe('Component Tests', () => {
  describe('Corona Management Detail Component', () => {
    let comp: CoronaDetailComponent;
    let fixture: ComponentFixture<CoronaDetailComponent>;
    const route = ({ data: of({ corona: new Corona(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DbfirstexoTestModule],
        declarations: [CoronaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CoronaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoronaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load corona on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.corona).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
