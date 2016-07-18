package poc.backend.enuns;

/**
 * @author Jharbas Araujo
 */
public enum TipoAtributoEnum {

	ALFANUMERICO(1, "Alfanumérico", "VARCHAR"), NUMERICO(2, "Numérico", "NUMERIC");

	private Integer codigo;
	private String nome;
	private String tipo;

	private TipoAtributoEnum(Integer codigo, String nome, String tipo) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
