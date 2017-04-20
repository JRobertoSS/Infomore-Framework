
package br.com.infomore.controle.web.command.impl;

import org.springframework.stereotype.Component;

import br.com.infomore.core.IFachada;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.EntidadeDominio;

@Component
public class AlterarClassificacaoCommand implements InfomoreCommand{

	public Resultado execute(EntidadeDominio entidade) {
		
		return fachada.alterar(entidade, "alterarClassificacao");
	}

}
