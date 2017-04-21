package br.com.infomore.core.impl.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import br.com.infomore.core.IDAO;
import br.com.infomore.core.IFachada;
import br.com.infomore.core.IStrategy;
import br.com.infomore.core.aplicacao.Resultado;

import br.com.infomore.dominio.EntidadeDominio;


@Resource
public class Fachada implements IFachada {

	/**
	 * Mapa de DAOS, ser indexado pelo nome da entidade O valor  uma instncia
	 * do DAO para uma dada entidade;
	 */
	private Map<String, IDAO> daos;

	/**
	 * Mapa para conter as regras de negcio de todas operaes por entidade; O
	 * valor  um mapa que de regras de negcio indexado pela operação
	 */	
	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		String nmClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "salvar"); // validaes

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
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
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, tipoAlteracao);

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
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
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "excluir");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
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

		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "consultar");

		List<EntidadeDominio> consulta = new ArrayList<EntidadeDominio>();

		if (msg == null) {
			IDAO<Integer, EntidadeDominio> dao = daos.get(nmClasse);
			consulta.add((EntidadeDominio) dao.consultar(entidade, entidade.getId()));
		} else {
			resultado.setMsg(msg);
		}

		resultado.setEntidades(consulta);
		return resultado;

	}

	@Override
	public Resultado listar(EntidadeDominio entidade) {
	
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "consultar");

		List<EntidadeDominio> consulta = new ArrayList<EntidadeDominio>();

		if (msg == null) {
			IDAO<Integer, EntidadeDominio> dao = daos.get(nmClasse);

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
	
	public void setDaos(Map<String, IDAO> daos) {
		this.daos = daos;
	}
	
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
}
