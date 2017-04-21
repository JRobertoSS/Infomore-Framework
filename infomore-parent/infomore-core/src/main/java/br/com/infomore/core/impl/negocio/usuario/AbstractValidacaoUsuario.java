package br.com.infomore.core.impl.negocio.usuario;

import br.com.infomore.core.IDAO;
import br.com.infomore.core.impl.dao.UsuarioDAO;

public class AbstractValidacaoUsuario {
	protected UsuarioDAO usuarioDao;
	
	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
