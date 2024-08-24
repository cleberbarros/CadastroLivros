import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  getAssuntos(filter: string, page: number, size: number): Observable<any> {
    let params = new HttpParams();
    if (filter) {
      params = params.set('filter', filter);
    }
    params = params.set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params, withCredentials: true });
  }

  getAssuntoById(id: number): Observable<AssuntoDTO> {
    return this.http.get<AssuntoDTO>(`${this.apiUrl}/${id}`);
  }

  createAssunto(assunto: AssuntoDTO): Observable<AssuntoDTO> {
    return this.http.post<AssuntoDTO>(this.apiUrl, assunto,{ withCredentials: true });
  }

  updateAssunto(assunto: AssuntoDTO): Observable<AssuntoDTO> {
    return this.http.put<AssuntoDTO>(`${this.apiUrl}/${assunto.id}`, assunto,{ withCredentials: true });
  }

  deleteAssunto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`,{ withCredentials: true });
  }

}
