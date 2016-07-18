package poc.backend.dto;

import java.io.Serializable;

import poc.backend.enuns.TipoAtributoEnum;

/**
 * @author Jharbas Araujo
 */
public class AtributoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer idModelo;
	private String nome;
	private String label;
	private TipoAtributoEnum tipo;
	private Integer precisao;
	private Integer escala;
	private String indObrigatorio;
	private String indPk;
	private Object valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Integer idModelo) {
		this.idModelo = idModelo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public TipoAtributoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtributoEnum tipo) {
		this.tipo = tipo;
	}

	public Integer getPrecisao() {
		return precisao;
	}

	public void setPrecisao(Integer precisao) {
		this.precisao = precisao;
	}

	public Integer getEscala() {
		return escala;
	}

	public void setEscala(Integer escala) {
		this.escala = escala;
	}

	public String getIndObrigatorio() {
		return indObrigatorio;
	}

	public void setIndObrigatorio(String indObrigatorio) {
		this.indObrigatorio = indObrigatorio;
	}

	public String getIndPk() {
		return indPk;
	}

	public void setIndPk(String indPk) {
		this.indPk = indPk;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}
