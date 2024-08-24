import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro, LivrosResponse } from '../models/livro.model';
import { environment } from '../../../../environment';
import { VendaDTO } from '../models/venda.model';

@Injectable({
  providedIn: 'root'
})
export class VendaService {
  private apiUrl = `${environment.apiUrl}/venda`;

  constructor(private http: HttpClient) { }

 

  buscarPorDescricao(descricao: string): Observable<VendaDTO[]> {
    let params = new HttpParams()
    return this.http.get<VendaDTO[]>(`${this.apiUrl}/buscar-por-descricao/${descricao}`, {params,withCredentials: true}); {
     
    };
  }

}
