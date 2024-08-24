import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivroListComponent } from './livros/livro-list/livro-list.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: '', component: LoginComponent }, 
  { path: 'livrolist', component: LivroListComponent, canActivate: [AuthGuard] }, 
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
