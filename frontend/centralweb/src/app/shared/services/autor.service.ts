import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
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


  getAutores(filter: string, page: number, size: number): Observable<any> {
    let params = new HttpParams();
    if (filter) {
      params = params.set('filter', filter);
    }
    params = params.set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params, withCredentials: true });
  }

  getAssuntoById(id: number): Observable<AutorDTO> {
    return this.http.get<AutorDTO>(`${this.apiUrl}/${id}`);
  }

  createAutor(autor: AutorDTO): Observable<AutorDTO> {
    return this.http.post<AutorDTO>(this.apiUrl, autor,{ withCredentials: true });
  }

  updateAutor(autor: AutorDTO): Observable<AutorDTO> {
    return this.http.put<AutorDTO>(`${this.apiUrl}/${autor.id}`, autor,{ withCredentials: true });
  }

  deleteAutor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`,{ withCredentials: true });
  }


}
