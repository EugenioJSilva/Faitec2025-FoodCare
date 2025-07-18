import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserReadService } from './user-read.service';
import { first, firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { User } from '../../domain/model/user';

@Injectable({
  providedIn: 'root'
})
export class UserUpdateService {

  constructor(private http: HttpClient, private userReadService: UserReadService) { }

  async update(id: string, name: string): Promise<any>{
    let userToUpdate: User = await this.userReadService.findById(id);
    if(userToUpdate == null){
      throw new Error('Usuário não encontrado');
    }

    userToUpdate.name = name;

    return firstValueFrom(this.http.put<any>(`${environment.api_endpoint}/user/${id}`, userToUpdate));
  }

  async updatePassword(id: string, newPassword: string): Promise<any>{
    const userToUpdate: User | null = await this.userReadService.findById(id);

    if(!userToUpdate){
      throw new Error('Usuário não encontrado');  
    }

    userToUpdate.password = newPassword;

    return await firstValueFrom(this.http.put<User>(`${environment.api_endpoint}/user/${id}`, userToUpdate));
  }

}
