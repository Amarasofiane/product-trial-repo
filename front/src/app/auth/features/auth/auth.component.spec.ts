import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthComponent } from './auth.component';
import { UserAuthService } from 'app/auth/data-access/user-auth-service.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('AuthComponent', () => {
  let component: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;
  let userAuthService: jest.Mocked<UserAuthService>;
  let router: jest.Mocked<Router>;

  beforeEach(async () => {
    const userAuthServiceMock = {
      login: jest.fn(),
      setToken: jest.fn(),
    };

    const routerMock = {
      navigate: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [AuthComponent],
      providers: [
        { provide: UserAuthService, useValue: userAuthServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    userAuthService = TestBed.inject(UserAuthService) as jest.Mocked<UserAuthService>;
    router = TestBed.inject(Router) as jest.Mocked<Router>;
  });

  it('should call login and navigate on successful login', () => {
    userAuthService.login.mockReturnValue(of({ token: 'test-token', role: 'admin' }));

    component.username = 'sofiane';
    component.role = 'admin';
    component.login();

    expect(userAuthService.login).toHaveBeenCalledWith('sofiane', 'admin');
    expect(userAuthService.setToken).toHaveBeenCalledWith('test-token', 'admin');
    expect(router.navigate).toHaveBeenCalledWith(['/products/list']);
  });

  it('should display an error message on login failure', () => {
    userAuthService.login.mockReturnValue(throwError(() => new Error('Login failed')));

    component.username = 'invalid';
    component.role = 'user';
    component.login();

    expect(userAuthService.login).toHaveBeenCalledWith('invalid', 'user');
    expect(component.errorMessage()).toBe('Invalid username and role');
  });
});
