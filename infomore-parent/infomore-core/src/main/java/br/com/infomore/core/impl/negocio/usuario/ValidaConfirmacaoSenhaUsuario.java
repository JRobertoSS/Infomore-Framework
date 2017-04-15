package br.com.infomore.core.impl.negocio.usuario;

import br.com.infomore.core.IStrategy;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.SenhaUsuario;
import br.com.infomore.dominio.Usuario;

public class ValidaConfirmacaoSenhaUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Usuario usuario = (Usuario) entidade;

		SenhaUsuario senhaUsuario = usuario.getSenhaUsuario();

		// confirmao de senha no bate com a nova senha?
		if (!senhaUsuario.getSenhaNova().equals(senhaUsuario.getConfirmaSenhaNova()))
			return "Confirmao de senha incorreta!";

		return null;

	}

}
