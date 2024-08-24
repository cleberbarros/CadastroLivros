import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro, LivrosResponse } from '../models/livro.model';
import { environment } from '../../../../environment';
import { AssuntoDTO } from '../models/assunto.model';

@Injectable({
  providedIn: 'root'
})
export class AssuntoService {
  private apiUrl = `${environment.apiUrl}/assuntos`;

  constructor(private http: HttpClient) { }

 

  buscarPorDescricao(descricao: string): Observable<AssuntoDTO[]> {
    let params = new HttpParams()
    return this.http.get<AssuntoDTO[]>(`${this.apiUrl}/buscar-por-descricao/${descricao}`, {params,withCredentials: true}); {
     
    };
  }

}
