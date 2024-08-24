import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro, LivrosResponse } from '../models/livro.model';
import { environment } from '../../../../environment';
import { AutorDTO } from '../../shared/models/autor.model';

@Injectable({
  providedIn: 'root'
})
export class AutorService {
  private apiUrl = `${environment.apiUrl}/autor`;

  constructor(private http: HttpClient) { }

 

  buscarPorNome(nome: string): Observable<AutorDTO[]> {
    let params = new HttpParams()
    return this.http.get<AutorDTO[]>(`${this.apiUrl}/buscar-por-nome/${nome}`, {params,withCredentials: true}); {
     
    };
  }

}
