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
public class CadastroController extends InfomoreController{

	@RequestMapping("cadastro")
	public String execute(){
		return "cadastro";
	}
	
	@RequestMapping("cadastrar")
	public String cadastrar(Usuario usuario, HttpServletRequest request){
		Resultado resultado = processar(usuario, request, Acao.SALVAR);
		
	  if( resultado.getMsg() == null ) {
	    request.getSession().setAttribute("usuario", usuario);
	    setMensagem( request.getSession(), getMensagemSucesso("Usuario", "cadastrado"), TipoMensagemView.MSG_SUCESSO);
	    return "classificacao";
	    
	  }
	  setMensagem( request.getSession(), resultado.getMsg(), TipoMensagemView.MSG_ALERTA);
	  return "cadastro";
	}

}
