package poc.backend.jsf;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Jharbas Araujo
 */
@Component("popupBBean")
@Scope("session")
public class PopupBBean extends GenericBBean {

	private String titulo;
	private String mensagem;

	@PostConstruct
	public void inicio() {
		this.setTitulo("Poc");
		this.setMensagem("");
	}

	public void close() {
		closeDialog();
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
