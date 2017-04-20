
package br.com.infomore.controle.web.command.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.infomore.controle.web.command.ICommand;
import br.com.infomore.core.IFachada;
import br.com.infomore.core.impl.controle.Fachada;


public interface InfomoreCommand extends ICommand {
		
	IFachada fachada = new Fachada();
	
}
