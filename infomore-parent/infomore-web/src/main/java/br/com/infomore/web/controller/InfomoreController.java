package br.com.infomore.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.infomore.controle.web.command.ICommand;
import br.com.infomore.controle.web.view.TipoMensagemView;
import br.com.infomore.core.aplicacao.Resultado;
import br.com.infomore.dominio.EntidadeDominio;

public class InfomoreController {

	@Autowired
	protected Map<String, ICommand> commands;
	
	protected Map<String, String> mapaMensagem;


	public Resultado processar(EntidadeDominio entidade, String acao){
		return commands.get( acao ).execute(entidade);
	}
	
	protected void setMensagem(String mensagem, String tipoMensagem){
		mapaMensagem = new HashMap<>();
		mapaMensagem.put("mensagem", mensagem);
		mapaMensagem.put("tipoMensagem", tipoMensagem);
	}
	
	protected String getMensagemSucesso( String entidade, String operacao){
		return entidade + " " + operacao + " com sucesso!"; 
	}
	
	
	public Map<String, ICommand> getCommands() {
		return commands;
	}

	public void setCommands(Map<String, ICommand> commands) {
		this.commands = commands;
	}
	
	
	
}
