import { Injectable } from '@angular/core';
import { UserCredentialDto } from '../../domain/dto/user-credential-dto';
import { Observable, map } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment.development';
import { User } from '../../domain/model/user';
import { AuthenticatedUserDto } from '../../domain/dto/authenticated-user-dto';

/**
 * Serviço de autenticação do frontend.
 * Gerencia login, logout e persistência de sessão no localStorage.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {}

  /**
   * Autentica usuário no backend Spring Boot.
   * Valida credenciais e retorna dados do usuário autenticado.
   */
  authenticate(credentials: UserCredentialDto): Observable<any> {
    console.log("Autenticando o usuario");

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    const urlCredentials = `${environment.authentication_api_endpoint}/authenticate`;

    return this.http.post<any>(urlCredentials, credentials, {headers}).pipe(
      map(response => {
        console.log('Resposta completa do backend:', response);
        if(response && response.user){
          console.log('Usuário extraido da resposta:', response.user);
          return response.user;
        }else{
          throw new Error('Credenciais inválidas')
        }
      })
    )
  }

  /** Armazena dados do usuário no localStorage para persistência de sessão */
  addDataToLocalStorage(email: string, name: string, token: string, userType: string): void {
    console.log('adicionando dados no cache...');
    localStorage.setItem('email', email);
    localStorage.setItem('fullname', name);
    localStorage.setItem('token', token);
    localStorage.setItem('userType', userType);
  }

  /** Verifica se o usuário está autenticado */
  isAuthenticated(): boolean {
    if (typeof localStorage === 'undefined') {
      return false;
    }
    return localStorage.getItem('user') !== null;
  }

  /** Recupera dados do usuário atual do localStorage */
  getCurrentUser(): User | null {
    if (typeof localStorage === 'undefined') {
      return null;
    }
    const userJson = localStorage.getItem('user');
    return userJson ? JSON.parse(userJson) as User : null;
  }

    getAuthenticateUser(): AuthenticatedUserDto{
    let email = localStorage.getItem('email');
    let name = localStorage.getItem('name');
    let token = localStorage.getItem('token');
    let userType = localStorage.getItem('userType');
    
    if(email == null  || name == null || token == null ||userType == null) {
      throw new Error('Dados invalidos');
    }

    let user: AuthenticatedUserDto = {
      email: email,
      name: name,
      token: token,
      userType: userType,
    };

    return user;
  }

  /** Remove dados do usuário e encerra sessão */
  logout(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem('user');
    }
  }

}
