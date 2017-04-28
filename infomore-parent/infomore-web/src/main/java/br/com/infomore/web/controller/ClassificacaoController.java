package br.com.infomore.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import br.com.infomore.controle.web.view.ClassificacaoView;
import br.com.infomore.controle.web.view.util.Acao;
import br.com.infomore.core.IFachada;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.core.impl.controle.Fachada;
import br.com.infomore.dominio.Categoria;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.Prioridade;
import br.com.infomore.dominio.Usuario;

@Controller
public class ClassificacaoController extends InfomoreController  implements ServletContextAware, InitializingBean {
	

	
	private List<Categoria> categorias;
	private List<ClassificacaoView> listaClassificacaoView;
	
	@PostConstruct
	public void init(){
		Resultado resultado = processar(new Categoria(), Acao.LISTAR); 

		categorias = new ArrayList<>();
		
		for( EntidadeDominio entidade : resultado.getEntidades()){
			categorias.add( (Categoria) entidade );
		}

		listaClassificacaoView = new ArrayList<>();
		for (Categoria categoria : categorias ) {
			// ignorar a categoria Meu Local
			if (!categoria.getNome().equals(Categoria.MEU_LOCAL)) {
				ClassificacaoView classificacaoView = new ClassificacaoView();
				classificacaoView.setCategoria(categoria);
				classificacaoView.setNomeIcone(ClassificacaoView.mapaIcone.get(categoria.getNome()));
				classificacaoView.setNomeId(ClassificacaoView.mapaNomeId.get(categoria.getNome()));
				listaClassificacaoView.add(classificacaoView);
			}
		}
	}
	
	@RequestMapping(value="classificacao")
	public ModelAndView classificacao(List<Prioridade> prioridades, HttpServletRequest request){
		request.setAttribute("listaClassificacaoView", listaClassificacaoView);
		return new ModelAndView("redirect:/mapa");
	}
	
	@RequestMapping(value = "alterarClassificacao")
	public ModelAndView alterarClassificacao(Usuario usuario){
		return new ModelAndView("cadastro");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setAttribute("listaClassificacaoView", listaClassificacaoView);
		
	}


}
