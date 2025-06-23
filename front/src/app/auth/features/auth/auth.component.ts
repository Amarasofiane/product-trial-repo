import { Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';

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
  ],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent {
  username: string = '';
  password: string = '';
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  constructor(private router: Router) { }

  login() {
    this.loading.set(true);
    setTimeout(() => {
      this.loading.set(false);
      if (this.username === 'admin' && this.password === 'password') {
        this.router.navigate(['/products/list']);
      } else {
        this.errorMessage.set('Invalid username or password');
      }
    }, 2000);
  }
}