package br.com.infomore.core.impl.negocio.usuario;

import br.com.infomore.core.IStrategy;
import br.com.infomore.core.impl.dao.UsuarioDAO;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.Usuario;

public class ValidaEmailUnicoUsuario extends AbstractValidacaoUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Usuario usuario = (Usuario) entidade;

		Usuario consulta = usuarioDao.consultarPorEmail(usuario.getEmail());

		// encontrou um e-mail igual de outro usurio?
		if (consulta != null && consulta.getId() != usuario.getId())
			return "E-mail " + usuario.getEmail() + " já cadastrado!";

		return null;
	}

}
