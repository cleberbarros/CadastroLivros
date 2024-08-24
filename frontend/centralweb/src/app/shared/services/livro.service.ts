import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro, LivrosResponse } from '../models/livro.model';
import { environment } from '../../../../environment';
import { AutorDTO } from '../../shared/models/autor.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LivroService {
  
  private apiUrl = `${environment.apiUrl}/livros`;

  constructor(private http: HttpClient,private snackBar: MatSnackBar) { }

  getLivros(filter?: string, page: number = 0, size: number = 10): Observable<LivrosResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', "id");;

    if (filter) {
      params = params.append('filter', filter);
    }
    return this.http.get<LivrosResponse>(`${this.apiUrl}` , { params, withCredentials: true });
  }

  createLivro(livroDTO: any): Observable<Livro> {
    return this.http.post<Livro>(`${this.apiUrl}`, livroDTO, { withCredentials: true });
  }

  updateLivro(id: number, livroDTO: { titulo: string; editora: string; edicao: string; anoPublicacao: string; autores: AutorDTO[] }): Observable<Livro> {
    return this.http.put<Livro>(`${this.apiUrl}/${id}`, livroDTO, { withCredentials: true });
  }

  deleteLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

    
    downloadLivrosRelatorio(): Observable<Blob> {
      return this.http.get(`${this.apiUrl}/livros-detalhados`, {
        responseType: 'blob',
        withCredentials: true
      });
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
      let errorMessage = 'Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.';
      
      if (error.error instanceof ErrorEvent) {
          errorMessage = `Ocorreu um erro: ${error.error.message}`;
      } else {
          if (error.error && error.error.message) {
              errorMessage = error.error.message; 
          } else {
              errorMessage = `Erro: ${error.statusText}. Por favor, tente novamente mais tarde.`;
          }
      }
  
      return throwError(() => new Error(errorMessage));
  }
  

}
