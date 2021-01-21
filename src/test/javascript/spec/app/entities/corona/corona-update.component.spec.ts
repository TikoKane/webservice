import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DbfirstexoTestModule } from '../../../test.module';
import { CoronaUpdateComponent } from 'app/entities/corona/corona-update.component';
import { CoronaService } from 'app/entities/corona/corona.service';
import { Corona } from 'app/shared/model/corona.model';

describe('Component Tests', () => {
  describe('Corona Management Update Component', () => {
    let comp: CoronaUpdateComponent;
    let fixture: ComponentFixture<CoronaUpdateComponent>;
    let service: CoronaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DbfirstexoTestModule],
        declarations: [CoronaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CoronaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoronaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoronaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Corona(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Corona();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
