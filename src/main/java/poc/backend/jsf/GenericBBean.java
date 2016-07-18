package poc.backend.jsf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

/**
 * @author Jharbas Araujo
 */
public class GenericBBean {

	private static final int POPUP_WIDTH = 200;
	private static final int POPUP_HEIGHT = 110;
	protected static final String POC_HOME = "index.jsf";
	protected static final String POC_MODELO = "modelo.jsf";

	protected RequestContext getContext() {
		return RequestContext.getCurrentInstance();
	}

	public void reloadPage() {
		getContext().execute("location.reload()");
	}

	public void showMessage(javax.faces.application.FacesMessage.Severity severidade, String mensagem) {
		getContext().showMessageInDialog(new FacesMessage(severidade, "BackEnd", mensagem));
	}

	public void openInfoDialog(String outcome, int width, int height) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("closable", false);
		options.put("height", (height == 0) ? POPUP_HEIGHT : height);
		options.put("width", (width == 0) ? POPUP_WIDTH : width);
		if (width == 0) {
			options.put("contentWidth", "\"100%\"");
		}
		getContext().openDialog(outcome, options, null);
	}

	public void goTo(String page) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(page);
	}

	public void closeDialog() {
		getContext().closeDialog(null);
		reloadPage();
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected boolean hasWhitespace(String value) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

}
