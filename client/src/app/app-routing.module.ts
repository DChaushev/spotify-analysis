import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TracksComponent } from './tracks/tracks.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'toptracks', component: TracksComponent },
  { path: '', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
