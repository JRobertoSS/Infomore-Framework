package br.com.infomore.web.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.controle.web.view.util.Acao;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.Usuario;

@Controller
public class CadastroController extends InfomoreController{

	@RequestMapping(value = "cadastro", method = RequestMethod.GET)
	public ModelAndView execute(Usuario usuario){
		return new ModelAndView("cadastro");
	}
	
	@RequestMapping(value = "cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(Usuario usuario, HttpServletRequest request){
		usuario.setExecutarWizard(true);
		Resultado resultado = processar(usuario, Acao.SALVAR);
		
	  if( resultado.getMsg() == null ) {
	    request.getSession().setAttribute("usuario", usuario);
	    setMensagem( getMensagemSucesso("Usuario", "cadastrado"), TipoMensagemView.MSG_SUCESSO);
	    return new ModelAndView("redirect:classificacao", "mapaMensagem", mapaMensagem);
	    
	  }
	  setMensagem( resultado.getMsg(), TipoMensagemView.MSG_ALERTA);
	  return new ModelAndView("cadastro", "mapaMensagem", mapaMensagem);
	}

}
