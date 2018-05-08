import { NavbarModule } from './../navbar/navbar.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  imports: [
    CommonModule,
    NoopAnimationsModule,
    NavbarModule
  ],
  declarations: [],
  exports: [NavbarModule]
})
export class CoreModule { }
