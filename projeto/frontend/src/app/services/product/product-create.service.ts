import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../../domain/model/product';
import { environment } from '../../../environments/environment.development';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ProductCreateService {

  constructor(private http: HttpClient) {}

  async create(product: Product): Promise<Product>{
    return await firstValueFrom(this.http.post<Product>(`${environment.api_endpoint}/product`, product));
  }
}
