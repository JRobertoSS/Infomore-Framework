package br.com.infomore.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.controle.web.view.util.Acao;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.Usuario;

@Controller
public class LoginController extends InfomoreController {

	@RequestMapping("")
	public ModelAndView paginaInicial(Usuario usuario) {
		return new ModelAndView("login");
	}

	@RequestMapping("login")
	public ModelAndView paginaLogin(Usuario usuario) {
		return new ModelAndView("login");
	}

	@RequestMapping("efetuaLogin")
	public ModelAndView efetuaLogin(Usuario usuario, HttpServletRequest request) {
		Resultado resultado = processar(usuario, Acao.CONSULTAR);

		if (resultado.getMsg() == null) {
			usuario = (Usuario) resultado.getEntidades().get(0);
			request.getSession().setAttribute("usuario", usuario);
			return usuario.isExecutarWizard() ? new ModelAndView("classificacao") : new ModelAndView("mapa");

		}
		setMensagem(resultado.getMsg(), TipoMensagemView.MSG_ALERTA);
		return new ModelAndView("login", "mapaMensagem", mapaMensagem);
	}

	@RequestMapping("logout")
	public String efetuaLogout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
