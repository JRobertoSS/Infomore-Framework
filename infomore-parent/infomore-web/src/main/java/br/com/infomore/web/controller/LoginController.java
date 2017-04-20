package br.com.infomore.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.controle.web.view.util.Acao;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.Usuario;

@Controller
public class LoginController extends InfomoreController{
	
	@RequestMapping("")
	public String paginaInicial(){
		return "login";
	}
	
	@RequestMapping("login")
	public String paginaLogin(){
		return "login";
	}
	
	@RequestMapping("efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpServletRequest request) {
	  Resultado resultado = processar(usuario, request, Acao.CONSULTAR);
	  
	  if( resultado.getMsg() == null ) {
		usuario = (Usuario) resultado.getEntidades().get(0);
	    request.getSession().setAttribute("usuario", usuario);
	    return usuario.isExecutarWizard() ? "classificacao" : "mapa";
	    
	  }
	  setMensagem(request.getSession(), resultado.getMsg(), TipoMensagemView.MSG_ALERTA);
	  return "login";
	}
	
	@RequestMapping("logout")
	public String efetuaLogout(HttpSession session) {
	  session.invalidate();
	  return "redirect:login";
	}
}
