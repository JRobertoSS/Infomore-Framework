
package br.com.infomore.core.aplicacao;

import java.util.List;

import br.com.infomore.dominio.EntidadeDominio;

public class Resultado extends EntidadeAplicacao {

	private String msg;
	private List<EntidadeDominio> entidades;
	/**
	 * Mtodo de recuperao do campo msg
	 *
	 * @return valor do campo msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * Valor de msg atribudo a msg
	 *
	 * @param msg Atributo da Classe
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * Mtodo de recuperao do campo entidades
	 *
	 * @return valor do campo entidades
	 */
	public List<EntidadeDominio> getEntidades() {
		return entidades;
	}
	/**
	 * Valor de entidades atribudo a entidades
	 *
	 * @param entidades Atributo da Classe
	 */
	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}
	
	
	
	
}
