package poc.backend.jsf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.enuns.TipoAtributoEnum;
import poc.backend.service.ModeloService;

/**
 * @author Jharbas Araujo
 */
@Component("modeloBBean")
@Scope("session")
public class ModeloBBean extends GenericBBean {

	private static Logger logger = Logger.getLogger(ModeloBBean.class.getName());

	private ModeloDto dtoFiltro;
	private ModeloDto dtoManutencao;
	private AtributoDto dtoAtributo;
	private List<ModeloDto> listaModelo;
	private boolean showResult;
	private boolean indEdit;
	private boolean indDetalhar;

	private static String GO_INCLUSAO = "incluir-modelo.jsf";

	@Autowired
	private ModeloService modeloService;

	@PostConstruct
	public void init() {
		dtoFiltro = new ModeloDto();
		dtoManutencao = new ModeloDto();
		dtoAtributo = new AtributoDto();
		listaModelo = new ArrayList<>();
		showResult = false;
		indEdit = false;
		indDetalhar = false;
	}

	/**
	 * pesquisa modelos na base de dados, filtrando por nome caso informado
	 */
	public void pesquisar() {
		showResult = false;
		try {
			logger.info("pesquisar :: Inicio");
			listaModelo = modeloService.pesquisarModelo(dtoFiltro);
			showResult = true;
			reloadPage();
			logger.info("pesquisar :: Fim");
		} catch (Exception e) {
			showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	/**
	 * valida os campos do modelo salva o registro
	 */
	public void salvarModelo() {
		try {
			indDetalhar = false;
			if (validarCamposModelo()) {

				// salva o modelo
				modeloService.salvarModelo(dtoManutencao);

				// direciona para tela de pesquisa
				listaModelo = modeloService.pesquisarModelo(new ModeloDto());
				showResult = true;
				goTo(POC_MODELO);

			}
		} catch (Exception e) {
			showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	/**
	 * valida os campos do modelo, retorna booleano indicando se esta preenchido
	 * corretamente
	 * 
	 * @return
	 */
	private Boolean validarCamposModelo() {

		// valida os campos obrigatorios
		StringBuilder camposNaoPreenchidos = new StringBuilder();

		if (StringUtils.isBlank(dtoManutencao.getNome())) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_NOME_MODELO));
		}

		if (StringUtils.isBlank(dtoManutencao.getLabel())) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_LABEL_MODELO));
		}

		if (camposNaoPreenchidos.length() > 0) {
			showMessage(FacesMessage.SEVERITY_WARN,
					Constantes.WARN_CAMPOS_OBRIGATORIOS.concat(camposNaoPreenchidos.toString()));
			return Boolean.FALSE;
		}

		// verifica valores invalidos
		if (hasWhitespace(dtoAtributo.getNome())) {
			showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_NOME_INVALIDO);
			return Boolean.FALSE;
		}

		if (CollectionUtils.isEmpty(dtoManutencao.getAtributos())) {
			showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_ATRIBUTO_OBRIGATORIO);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	/**
	 * direciona o modelo escolhido para a tela de detalhe
	 * 
	 * @param modelo
	 */
	public void goDetalhar(ModeloDto modelo) {
		try {
			logger.info("goDetalhar :: Inicio");
			indDetalhar = true;
			dtoManutencao = modeloService.pesquisarModeloCompleto(modelo.getId());
			goTo(GO_INCLUSAO);
			logger.info("goDetalhar :: Fim");
		} catch (Exception e) {
			showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	/**
	 * direciona e prepara a tela de inclusao
	 */
	public void goIncluir() {
		try {
			indEdit = false;
			indDetalhar = false;
			dtoManutencao = new ModeloDto();
			dtoManutencao.setAtributos(new ArrayList<AtributoDto>());
			dtoAtributo = new AtributoDto();
			dtoAtributo.setIndObrigatorio(Constantes.SIM);

			AtributoDto pk = new AtributoDto();
			pk.setNome(Constantes.COL_ID);
			pk.setLabel(Constantes.COL_ID);
			pk.setTipo(TipoAtributoEnum.NUMERICO);
			pk.setIndObrigatorio(Constantes.SIM);
			pk.setIndPk(Constantes.SIM);
			pk.setPrecisao(8);

			dtoManutencao.getAtributos().add(pk);

			goTo(GO_INCLUSAO);
		} catch (IOException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	/**
	 * envia o atributo selecionado para o formulario, para que possa ser
	 * editado
	 * 
	 * @param atributoDto
	 */
	public void editarAtributo(AtributoDto atributoDto) {
		indEdit = true;
		dtoAtributo = atributoDto;
	}

	/**
	 * abre a janela de confirmacao de exclusao de atributo
	 * 
	 * @param atributoDto
	 */
	public void excluirAtributo(AtributoDto atributoDto) {
		dtoManutencao.getAtributos().remove(atributoDto.getId().intValue());
		resetIndexList();
		reloadPage();
	}

	/**
	 * limpa o formulario de inclusao de atributos
	 */
	public void limparAtributo() {
		dtoAtributo = new AtributoDto();
		dtoAtributo.setIndObrigatorio(Constantes.SIM);

		indEdit = false;

		reloadPage();
	}

	/**
	 * Valida os campos do atributo e inclui na lista
	 */
	public void salvarAtributo() {
		try {
			// Valida campos obrigatorios
			if (validarCamposAtributo()) {

				dtoAtributo.setIndPk(Constantes.NAO);
				dtoAtributo.setNome(dtoAtributo.getNome().toUpperCase());
				if (!dtoAtributo.getTipo().equals(TipoAtributoEnum.NUMERICO)) {
					dtoAtributo.setEscala(null);
				} else {
					if (dtoAtributo.getEscala() == null) {
						dtoAtributo.setEscala(0);
					}
				}

				boolean duplicado = false;
				for (AtributoDto atr : dtoManutencao.getAtributos()) {
					if (atr.getNome().equalsIgnoreCase(dtoAtributo.getNome())) {
						duplicado = true;
						break;
					}
				}

				if (duplicado) {
					showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_ATRIBUTO_DUPLICADO);
				} else {

					if (indEdit) {
						dtoManutencao.getAtributos().set(dtoAtributo.getId(), dtoAtributo);
					} else {
						dtoManutencao.getAtributos().add(dtoAtributo);
					}
					indEdit = false;
					resetIndexList();
					limparAtributo();
				}

			}

		} catch (Exception e) {
			showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	private void resetIndexList() {
		if (dtoManutencao != null && dtoManutencao.getAtributos() != null) {
			for (int i = 0; i < dtoManutencao.getAtributos().size(); i++) {
				dtoManutencao.getAtributos().get(i).setId(i);
			}
		}
	}

	/**
	 * valida os campos do atributo
	 * 
	 * @return
	 */
	private Boolean validarCamposAtributo() {

		// valida os campos obrigatorios
		StringBuilder camposNaoPreenchidos = new StringBuilder();

		if (StringUtils.isBlank(dtoAtributo.getNome())) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_NOME_ATRIBUTO));
		}

		if (StringUtils.isBlank(dtoAtributo.getLabel())) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_LABEL_ATRIBUTO));
		}

		if (dtoAtributo.getTipo() == null) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_TIPO));
		}

		if (dtoAtributo.getPrecisao() == null) {
			camposNaoPreenchidos.append(" - ".concat(Constantes.LBL_QTD_CARACTERES));
		}

		if (camposNaoPreenchidos.length() > 0) {
			showMessage(FacesMessage.SEVERITY_WARN,
					Constantes.WARN_CAMPOS_OBRIGATORIOS.concat(camposNaoPreenchidos.toString()));
			return Boolean.FALSE;
		}

		// verifica valores invalidos
		if (hasWhitespace(dtoAtributo.getNome())) {
			showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_NOME_INVALIDO);
			return Boolean.FALSE;
		}

		if (dtoAtributo.getPrecisao() < 1 || dtoAtributo.getPrecisao() > 500) {
			showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_PRECISAO_INVALIDO);
			return Boolean.FALSE;
		}

		if (dtoAtributo.getTipo() != null && dtoAtributo.getTipo().equals(TipoAtributoEnum.NUMERICO)
				&& dtoAtributo.getEscala() != null && (dtoAtributo.getEscala() < 0 || dtoAtributo.getEscala() > 10)) {
			showMessage(FacesMessage.SEVERITY_WARN, Constantes.WARN_ESCALA_INVALIDO);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	public TipoAtributoEnum[] getListaTipos() {
		return TipoAtributoEnum.values();
	}

	public ModeloDto getDtoFiltro() {
		return dtoFiltro;
	}

	public void setDtoFiltro(ModeloDto dtoFiltro) {
		this.dtoFiltro = dtoFiltro;
	}

	public ModeloDto getDtoManutencao() {
		return dtoManutencao;
	}

	public void setDtoManutencao(ModeloDto dtoManutencao) {
		this.dtoManutencao = dtoManutencao;
	}

	public List<ModeloDto> getListaModelo() {
		return listaModelo;
	}

	public void setListaModelo(List<ModeloDto> listaModelo) {
		this.listaModelo = listaModelo;
	}

	public boolean isShowResult() {
		return showResult;
	}

	public void setShowResult(boolean showResult) {
		this.showResult = showResult;
	}

	public AtributoDto getDtoAtributo() {
		return dtoAtributo;
	}

	public void setDtoAtributo(AtributoDto dtoAtributo) {
		this.dtoAtributo = dtoAtributo;
	}

	public boolean isIndDetalhar() {
		return indDetalhar;
	}

	public void setIndDetalhar(boolean indDetalhar) {
		this.indDetalhar = indDetalhar;
	}

}
