package br.com.infomore.core.impl.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.infomore.core.IFachada;
import br.com.infomore.core.IStrategy;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.core.impl.dao.AbstractDAO;
import br.com.infomore.core.impl.dao.CategoriaDAO;
import br.com.infomore.core.impl.dao.LimiteRaioDAO;
import br.com.infomore.core.impl.dao.PontoDAO;
import br.com.infomore.core.impl.dao.UsuarioDAO;
import br.com.infomore.core.impl.negocio.usuario.PreencheSenhaNovaUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaCamposAlteracaoPerfilUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaCamposAlteracaoSenhaUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaCamposCadastroUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaConfirmacaoSenhaUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaEmailUnicoUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaLoginUsuario;
import br.com.infomore.core.impl.negocio.usuario.ValidaSenhaAtualUsuario;
import br.com.infomore.dominio.Categoria;
import br.com.infomore.dominio.EntidadeDominio;
import br.com.infomore.dominio.LimiteRaio;
import br.com.infomore.dominio.Ponto;
import br.com.infomore.dominio.Usuario;


public class Fachada implements IFachada {

	/**
	 * Mapa de DAOS, ser indexado pelo nome da entidade O valor  uma instncia
	 * do DAO para uma dada entidade;
	 */
	private Map<String, AbstractDAO> daos;

	/**
	 * Mapa para conter as regras de negcio de todas operaes por entidade; O
	 * valor  um mapa que de regras de negcio indexado pela operação
	 */
	
	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;

	public Fachada() {

		/**
		 * -------------- MAPAS DE DAO E STRATEGY---------------------
		 */
		// mapa de Daos
		daos = new HashMap<String, AbstractDAO>();
	

		/**
		 * -------------- DAOS ---------------------
		 */
		// instncias
		UsuarioDAO usuarioDao = new UsuarioDAO();
		CategoriaDAO categoriaDao = new CategoriaDAO();
		PontoDAO pontoDao = new PontoDAO();
		LimiteRaioDAO limiteRaioDao = new LimiteRaioDAO();

		/* Adicionando cada dao no MAP indexando pelo nome da classe */
		daos.put(Usuario.class.getName(), usuarioDao);
		daos.put(Categoria.class.getName(), categoriaDao);
		daos.put(Ponto.class.getName(), pontoDao);
		daos.put(LimiteRaio.class.getName(), limiteRaioDao);

	
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "salvar"); // validaes

		if (msg == null) {
			AbstractDAO dao = daos.get(nmClasse);
			dao.salvar(entidade);
			List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
			entidades.add(entidade);
			resultado.setEntidades(entidades);
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade, String tipoAlteracao) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, tipoAlteracao);

		if (msg == null) {
			AbstractDAO dao = daos.get(nmClasse);
			dao.alterar(entidade);
			List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
			entidades.add(entidade);
			resultado.setEntidades(entidades);
		} else {
			resultado.setMsg(msg);

		}

		return resultado;

	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "excluir");

		if (msg == null) {
			AbstractDAO dao = daos.get(nmClasse);
			dao.excluir(entidade);
			List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
			entidades.add(entidade);
			resultado.setEntidades(entidades);
		} else {
			resultado.setMsg(msg);

		}

		return resultado;

	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<EntidadeDominio>(1));

		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "consultar");

		List<EntidadeDominio> consulta = new ArrayList<EntidadeDominio>();

		if (msg == null) {
			AbstractDAO<Integer, EntidadeDominio> dao = daos.get(nmClasse);
			consulta.add((EntidadeDominio) dao.consultar(entidade, entidade.getId()));
		} else {
			resultado.setMsg(msg);
		}

		resultado.setEntidades(consulta);
		return resultado;

	}

	@Override
	public Resultado listar(EntidadeDominio entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<EntidadeDominio>(1));

		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "consultar");

		List<EntidadeDominio> consulta = new ArrayList<EntidadeDominio>();

		if (msg == null) {
			AbstractDAO<Integer, EntidadeDominio> dao = daos.get(nmClasse);

			consulta = dao.listar(entidade);

		} else {
			resultado.setMsg(msg);
		}

		resultado.setEntidades(consulta);
		return resultado;

	}

	private String executarRegras(EntidadeDominio entidade, String operacao) {
		String nmClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();

		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);

		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);

			if (regras != null) {
				for (IStrategy s : regras) {
					String m = s.processar(entidade);

					if (m != null) {
						msg.append(m);
						msg.append("\n");
						break; // parar as validaes para somente uma mensagem de erro
					}
				}
			}

		}

		if (msg.length() > 0)
			return msg.toString();
		else
			return null;
	}
	
	public void setRns(Map<String, Map<String, List<IStrategy>>> rns) {
		this.rns = rns;
	}
}
