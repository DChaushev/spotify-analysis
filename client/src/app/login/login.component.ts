import { Component } from '@angular/core';
import { environment } from '../../environments/environment';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  login() {
    window.location.href = `https://accounts.spotify.com/authorize?client_id=${environment.CLIENT_ID}&response_type=code&scope=user-top-read&redirect_uri=${environment.REDIRECT_URI}`;
  }
}
