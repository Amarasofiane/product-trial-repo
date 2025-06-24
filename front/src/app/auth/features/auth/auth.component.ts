

import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { UserAuthService } from 'app/auth/data-access/user-auth-service.service';
import { Router } from '@angular/router';
import { DropdownModule } from 'primeng/dropdown';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    CardModule,
    MessageModule,
    DropdownModule
  ],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent {
  roles = [
    { label: 'Admin', value: 'admin' },
    { label: 'User', value: 'user' }
  ];
  username = '';
  role: string = '';
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  constructor(private userAuthService: UserAuthService, private router: Router) { }

  login() {  // login de la page auth afin de rediriger vers le product list
    this.loading.set(true);
    this.userAuthService.login(this.username, this.role).subscribe({
      next: (response) => {
        this.loading.set(false);
        this.userAuthService.setToken(response.token, response.role);
        this.errorMessage.set('valid username');
        this.router.navigate(['/products/list']);
      },
      error: () => {
        this.errorMessage.set('Invalid username and role');
      },
    });
  }

}