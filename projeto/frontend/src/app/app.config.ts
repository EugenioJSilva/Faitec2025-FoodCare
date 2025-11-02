import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideToastr } from 'ngx-toastr';
import { provideNativeDateAdapter } from '@angular/material/core';
import { LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';


registerLocaleData(localePt);


import { routes } from './app.routes';
import { AuthInterceptor } from './services/security/interceptor/auth.interceptor';

export const appConfig: ApplicationConfig = {

  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    {
      provide: HTTP_INTERCEPTORS,
      useClass:  AuthInterceptor,
      multi: true
    },
    provideRouter(routes),
    provideAnimationsAsync(),  
    provideHttpClient(withInterceptorsFromDi()),
    provideToastr(),
    provideNativeDateAdapter(),
    { provide: LOCALE_ID, useValue: 'pt-BR' }
  ]
  
};
