package br.com.infomore.core;

import java.util.List;


import br.com.infomore.dominio.EntidadeDominio;

public interface IDAO<K, T> {

	void salvar(T objeto);

	void alterar(T objeto);

	void excluir(T objeto);

	T consultar(T objeto, K chave);

	List<T> listar();

	List<T> listar(EntidadeDominio entidade);

}
