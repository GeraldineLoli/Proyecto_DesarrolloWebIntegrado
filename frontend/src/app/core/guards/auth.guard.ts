import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router      = inject(Router);

  // Solo deja pasar si hay token y el rol es ADMIN
  if (authService.isAdmin()) {
    return true;
  }

  // Cualquier otro caso → redirige al login
  router.navigate(['/login']);
  return false;
};
