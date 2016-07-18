package poc.backend.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jharbas Araujo
 */
public class ModeloDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String label;
	private List<AtributoDto> atributos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<AtributoDto> getAtributos() {
		return atributos;
	}

	public void setAtributos(List<AtributoDto> atributos) {
		this.atributos = atributos;
	}

}
