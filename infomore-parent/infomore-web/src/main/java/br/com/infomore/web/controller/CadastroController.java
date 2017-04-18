package br.com.infomore.web.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.infomore.dominio.Usuario;

@Controller
public class CadastroController {

	@RequestMapping("cadastro")
	public String execute(){
		return "cadastro";
	}
	
	@RequestMapping("cadastrar")
	public String cadastrar(Usuario usuario, HttpSession session) {
		Usuario consulta = new Usuario(); // chamar fachada pra consultar se existe
		
	  if( consulta != null ) {
	    session.setAttribute("usuario", usuario);
	    return usuario.isExecutarWizard() ? "classificacao" : "mapa";
	    
	  }
	  return "redirect:login";
	}

}
