import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { TextMaskModule } from 'angular2-text-mask';
import { SegurancaRoutingModule } from './seguranca-routing.module';

/*import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material';*/


@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    SegurancaRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    TextMaskModule
  ],
  declarations: [LoginComponent],
  exports: [LoginComponent]
  /*providers: [
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500}}
  ]*/

})
export class SegurancaModule { }
