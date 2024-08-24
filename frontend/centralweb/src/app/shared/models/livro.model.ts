export interface Autor{
  id: number,
  nome: string
}

export interface Assunto{
  id: number,
  descricao: string
}

export interface Venda{
  id: number,
  descricao: string
}

export interface Livro {
  id: number;
  titulo: string;
  editora: string;
  edicao: number;
  anoPublicacao: string;
  autores: Autor[];
  assuntos: Assunto[];
  formatosVendas: Venda[];
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  offset: number;
  unpaged: boolean;
  paged: boolean;
}

export interface LivrosResponse {
  content: Livro[];
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  empty: boolean;
}
