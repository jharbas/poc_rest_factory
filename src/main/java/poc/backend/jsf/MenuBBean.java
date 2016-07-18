package poc.backend.jsf;

import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Jharbas Araujo
 */
@Component("menuBBean")
@Scope("session")
public class MenuBBean extends GenericBBean {

	private static Logger logger = Logger.getLogger(MenuBBean.class.getName());

	/**
	 * Direciona para a tela de modelo
	 * 
	 */
	public void goToModelo() {
		try {
			logger.info("redirecionando para a tela Modelo");
			FacesContext.getCurrentInstance().getExternalContext().redirect(POC_MODELO);
		} catch (IOException e) {
			logger.severe("Erro ao tentar redirecionar para a tela de cadastro de modelo: " + e.getMessage());
		}
	}

	/**
	 * Direciona para o home do projeto
	 * 
	 */
	public void goToHome() {
		try {
			logger.info("redirecionando para a tela Modelo");
			FacesContext.getCurrentInstance().getExternalContext().redirect(POC_HOME);
		} catch (IOException e) {
			logger.severe("Erro ao tentar redirecionar para a Home: " + e.getMessage());
		}
	}
}
