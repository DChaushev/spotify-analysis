import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TracksComponent } from './tracks/tracks.component';
import { LoginComponent } from './login/login.component';
import { CallbackComponent } from './callback/callback.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'toptracks', component: TracksComponent },
  { path: 'callback', component: CallbackComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
