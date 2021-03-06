
package br.com.infomore.controle.web.command.impl;

import org.springframework.stereotype.Component;

import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.EntidadeDominio;


public class ListarCommand extends AbstractInfomoreCommand {

	public Resultado execute(EntidadeDominio entidade) {

		return fachada.listar(entidade);
	}

}
