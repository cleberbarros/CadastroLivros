import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  getVendas(filter: string, page: number, size: number): Observable<any> {
    let params = new HttpParams();
    if (filter) {
      params = params.set('filter', filter);
    }
    params = params.set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params, withCredentials: true });
  }

  getVendaById(id: number): Observable<VendaDTO> {
    return this.http.get<VendaDTO>(`${this.apiUrl}/${id}`);
  }

  createVenda(venda: VendaDTO): Observable<VendaDTO> {
    return this.http.post<VendaDTO>(this.apiUrl, venda,{ withCredentials: true });
  }

  updateVenda(venda: VendaDTO): Observable<VendaDTO> {
    return this.http.put<VendaDTO>(`${this.apiUrl}/${venda.id}`, venda,{ withCredentials: true });
  }

  deleteVenda(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`,{ withCredentials: true });
  }


}
