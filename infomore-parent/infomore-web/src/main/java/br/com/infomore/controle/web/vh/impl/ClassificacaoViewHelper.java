package br.com.infomore.controle.web.vh.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.infomore.controle.web.vh.IViewHelper;
import br.com.infomore.controle.web.view.ClassificacaoView;
import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.Categoria;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.Prioridade;
import br.com.infomore.dominio.Usuario;

/**
 * Classe responsvel por construir o objeto que ir ser utilizado para
 * atualizar as definies da classificao (prioridades) das categorias
 * disponveis no sistema, alm de servir a pgina correta aps as operaes
 * 
 * @author Jos Roberto
 *
 */
public class ClassificacaoViewHelper implements IViewHelper {
	private ServletContext contexto;
	private final static String ATRIBUTO_CATEGORIAS = "categorias";
	private final static String ATRIBUTO_USUARIO = "usuario";

	private final static Integer ID_SAUDE = 0;
	private final static Integer ID_EDUCACAO = 1;
	private final static Integer ID_SEGURANCA = 2;
	private final static Integer ID_COMODIDADE = 3;
	private final static Integer ID_LAZER = 4;
	private final static Integer ID_TRANSPORTE = 5;
	private final static Integer ID_OCORRENCIA = 6;
	
	private Map<String, String> mapaValorPesoPorCategoria;

	public ClassificacaoViewHelper(ServletContext contexto) {
		this.contexto = contexto;
		
		mapaValorPesoPorCategoria = new HashMap<>();
		mapaValorPesoPorCategoria.put(Categoria.COMODIDADES, ClassificacaoView.INPUT_COMODIDADES);
		mapaValorPesoPorCategoria.put(Categoria.EDUCACAO, ClassificacaoView.INPUT_EDUCACAO);
		mapaValorPesoPorCategoria.put(Categoria.LAZER_CULTURA, ClassificacaoView.INPUT_LAZER);
		mapaValorPesoPorCategoria.put(Categoria.OCORRENCIAS, ClassificacaoView.INPUT_OCORRENCIAS);
		mapaValorPesoPorCategoria.put(Categoria.SAUDE, ClassificacaoView.INPUT_SAUDE);
		mapaValorPesoPorCategoria.put(Categoria.SEGURANCA, ClassificacaoView.INPUT_SEGURANCA);
		mapaValorPesoPorCategoria.put(Categoria.TRANSPORTES, ClassificacaoView.INPUT_TRANSPORTE);
	}

	@Override
	public EntidadeDominio getEntity(HttpServletRequest request) {

		// pega o usurio da sesso
		Usuario usuario = (Usuario) request.getSession().getAttribute(ATRIBUTO_USUARIO);

		// primeira configurao das prioridades?
		if (usuario.getPrioridades() == null || usuario.getPrioridades().isEmpty()) {
			// pegar as prioridades classificadas pelo usurio e associ-las ao
			// seu
			// perfil, utilizando
			// tambm as classificaes armazenadas no contexto

			Prioridade prioridadeSaude = new Prioridade();
			Prioridade prioridadeEducacao = new Prioridade();
			Prioridade prioridadeSeguranca = new Prioridade();
			Prioridade prioridadeComodidade = new Prioridade();
			Prioridade prioridadeLazer = new Prioridade();
			Prioridade prioridadeTransporte = new Prioridade();
			Prioridade prioridadeOcorrencia = new Prioridade();

			List<Categoria> categorias = (List<Categoria>) contexto.getAttribute(ATRIBUTO_CATEGORIAS);

			prioridadeSaude.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_SAUDE)));
			prioridadeSaude.setCategoria(categorias.get(ID_SAUDE));
			prioridadeSaude.setDescricao(categorias.get(ID_SAUDE).getDescricao());
			prioridadeSaude.setUsuario(usuario);

			prioridadeEducacao.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_EDUCACAO)));
			prioridadeEducacao.setCategoria(categorias.get(ID_EDUCACAO));
			prioridadeEducacao.setDescricao(categorias.get(ID_EDUCACAO).getDescricao());
			prioridadeEducacao.setUsuario(usuario);

			prioridadeSeguranca.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_SEGURANCA)));
			prioridadeSeguranca.setCategoria(categorias.get(ID_SEGURANCA));
			prioridadeSeguranca.setDescricao(categorias.get(ID_SEGURANCA).getDescricao());
			prioridadeSeguranca.setUsuario(usuario);

			prioridadeComodidade.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_COMODIDADES)));
			prioridadeComodidade.setCategoria(categorias.get(ID_COMODIDADE));
			prioridadeComodidade.setDescricao(categorias.get(ID_COMODIDADE).getDescricao());
			prioridadeComodidade.setUsuario(usuario);

			prioridadeLazer.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_LAZER)));
			prioridadeLazer.setCategoria(categorias.get(ID_LAZER));
			prioridadeLazer.setDescricao(categorias.get(ID_LAZER).getDescricao());
			prioridadeLazer.setUsuario(usuario);

			prioridadeTransporte.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_TRANSPORTE)));
			prioridadeTransporte.setCategoria(categorias.get(ID_TRANSPORTE));
			prioridadeTransporte.setDescricao(categorias.get(ID_TRANSPORTE).getDescricao());
			prioridadeTransporte.setUsuario(usuario);

			prioridadeOcorrencia.setPeso(Double.parseDouble(request.getParameter(ClassificacaoView.INPUT_OCORRENCIAS)));
			prioridadeOcorrencia.setCategoria(categorias.get(ID_OCORRENCIA));
			prioridadeOcorrencia.setDescricao(categorias.get(ID_OCORRENCIA).getDescricao());
			prioridadeOcorrencia.setUsuario(usuario);

			List<Prioridade> prioridades = new ArrayList<>();
			prioridades.add(prioridadeOcorrencia);
			prioridades.add(prioridadeComodidade);
			prioridades.add(prioridadeEducacao);
			prioridades.add(prioridadeLazer);
			prioridades.add(prioridadeSaude);
			prioridades.add(prioridadeSeguranca);
			prioridades.add(prioridadeTransporte);

			usuario.setPrioridades(prioridades);
			
		
											
		} else { // se j existem prioridades, atualizar os valores
			List<Prioridade> prioridades = new ArrayList<>();
			
			for( Prioridade p : usuario.getPrioridades() ){
				p.setPeso( Double.valueOf(
						request.getParameter( mapaValorPesoPorCategoria.get(
												p.getCategoria().getNome()))));
				prioridades.add(p);
			}
			// lista atualizada das prioridades
			usuario.setPrioridades(prioridades);
		}

		// para no entrar automaticamente nas classificaes no prx. login
		usuario.setExecutarWizard(false); 
		
		return usuario;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (resultado != null && resultado.getMsg() != null) {
			request.setAttribute("mensagem", resultado.getMsg());
			request.setAttribute("tipoMensagem", TipoMensagemView.MSG_ALERTA);
		} else {
			request.setAttribute("mensagem", "Classificaes atualizadas com sucesso!");
			request.setAttribute("tipoMensagem", TipoMensagemView.MSG_SUCESSO);
		}
		/*request.setAttribute("usuario", request.getSession().getAttribute("usuario"));*/
		RequestDispatcher d = request.getRequestDispatcher("view/mapa.jsp");

		d.forward(request, response);
	}

}
