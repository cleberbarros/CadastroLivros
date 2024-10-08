import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AuthService } from './shared/services/auth.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Cadastro de Livros';
  userName: string = '';
  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.authService.getUserInfo().subscribe(userInfo => {
      this.userName = userInfo.name;
    });
  }

  redirectToLivros(): void {
    this.router.navigate(['/livrolist']);
  }

  onLogout(): void {
    this.userName = ''; 
    this.authService.logout();
  }
}
