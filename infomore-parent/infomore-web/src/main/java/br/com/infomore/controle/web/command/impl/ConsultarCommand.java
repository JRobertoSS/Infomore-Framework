
package br.com.infomore.controle.web.command.impl;


import org.springframework.stereotype.Component;

import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.EntidadeDominio;

@Component
public class ConsultarCommand extends InfomoreCommand {

	public Resultado execute(EntidadeDominio entidade) {

		return fachada.consultar(entidade);
	}

}
