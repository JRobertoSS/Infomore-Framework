package br.com.infomore.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.infomore.controle.web.vh.IViewHelper;
import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.Usuario;

public class LoginViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntity(HttpServletRequest request) {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equals("sair"))
			return null;

		Usuario usuario = new Usuario();
		usuario.setEmail(request.getParameter("inputEmail"));
		usuario.setSenha(request.getParameter("inputSenha"));
		return usuario;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String acao = request.getParameter("acao");
		RequestDispatcher d = null;

		if (acao != null && acao.equals("sair")) {

			request.getSession().setAttribute("usuario", null);
			d = request.getRequestDispatcher("view/login.jsp");

		} else {

			Usuario usuario = resultado.getEntidades().isEmpty() ? null : (Usuario) resultado.getEntidades().get(0);

			if (resultado != null && resultado.getMsg() != null) {

				request.setAttribute("mensagem", resultado.getMsg());
				request.setAttribute("tipoMensagem", TipoMensagemView.MSG_ALERTA);
				d = request.getRequestDispatcher("view/login.jsp");

			} else if (usuario == null) { // no encontrou o usurio por e-mail?

				request.setAttribute("mensagem", "Usuario no cadastrado!");
				request.setAttribute("tipoMensagem", TipoMensagemView.MSG_ALERTA);
				d = request.getRequestDispatcher("view/login.jsp");

			} else { // encontrou

				if (usuario.getSenha().equals(request.getParameter("inputSenha"))) { // a
																						// senha
																						// digitada
																						// corresponde
																						// 
																						// cadastrada?

					request.getSession().setAttribute("usuario", usuario); // seta
																			// usurio
																			// na
																			// sesso

					if (usuario.isExecutarWizard()) { //  para executar a
														// classificao?
						d = request.getRequestDispatcher("view/classificacao.jsp");
					} else { // seno vai para o mapa
						d = request.getRequestDispatcher("view/mapa.jsp");
					}

				} else { // senha invlida
					request.setAttribute("mensagem", "Senha invlida!");
					request.setAttribute("tipoMensagem", TipoMensagemView.MSG_ALERTA);
					d = request.getRequestDispatcher("view/login.jsp");
				}
			}
		}
		request.setAttribute("resultado", resultado);
		d.forward(request, response);

	}

}
