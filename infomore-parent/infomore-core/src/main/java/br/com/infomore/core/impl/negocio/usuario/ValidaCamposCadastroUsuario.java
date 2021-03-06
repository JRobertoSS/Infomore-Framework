package br.com.infomore.core.impl.negocio.usuario;

import br.com.infomore.core.IStrategy;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.Usuario;

/***
 * 
 * @author JosRoberto Classe para validar se os campos nome, login e senha esto
 *         vazios
 */
public class ValidaCamposCadastroUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;

		if (usuario.getNome() == null || usuario.getEmail() == null || usuario.getSenhaUsuario() == null
				|| usuario.getSenhaUsuario().getSenhaNova() == null
				|| usuario.getSenhaUsuario().getConfirmaSenhaNova() == null || usuario.getDtNascimento() == null)
			return "Todos os campos são obrigatórios! ";

		if (usuario.getNome().trim().isEmpty() || usuario.getEmail().trim().isEmpty()
				|| usuario.getSenhaUsuario().getSenhaNova().trim().isEmpty()
				|| usuario.getSenhaUsuario().getConfirmaSenhaNova().trim().isEmpty()
				|| usuario.getDtNascimento() == null)
			return "Todos os campos são obrigatórios! ";

		return null;
	}

}
