package poc.backend.constant;

/**
 * @author Jharbas Araujo
 */
public final class Constantes {

	// indicadores
	public static final String SIM = "S";
	public static final String NAO = "N";

	// colunas
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_LABEL = "LABEL";
	public static final String COL_ID_MODELO = "ID_MODELO";
	public static final String COL_TIPO = "TIPO";
	public static final String COL_QTD_CARACTERES = "QTD_CARACTERES";
	public static final String COL_QTD_DECIMAIS = "QTD_DECIMAIS";
	public static final String COL_IND_OBRIGATORIO = "IND_OBRIGATORIO";
	public static final String COL_NEXTID = "NEXTID";

	// tabelas
	public static final String TB_MODELO = "TB_MODELO";
	public static final String TB_ATRIBUTO = "TB_ATRIBUTO";

	// campos
	public static final String LBL_NOME_MODELO = "Nome do Modelo";
	public static final String LBL_LABEL_MODELO = "Label do Modelo";
	public static final String LBL_NOME_ATRIBUTO = "Nome do Atributo";
	public static final String LBL_LABEL_ATRIBUTO = "Label do Atributo";
	public static final String LBL_TIPO = "Tipo";
	public static final String LBL_QTD_CARACTERES = "Qtd. Caracteres";
	public static final String LBL_CASAS_DECIMAIS = "Casas Decimais";

	// queries
	public static final String QUERY_CRIAR_TB_MODELO = "CREATE TABLE TB_MODELO (ID INT PRIMARY KEY, NOME VARCHAR(20), LABEL VARCHAR(20))";
	public static final String QUERY_CRIAR_TB_ATRIBUTO = "CREATE TABLE TB_ATRIBUTO (ID INT PRIMARY KEY, ID_MODELO INT, NOME VARCHAR(20), LABEL VARCHAR(20), TIPO VARCHAR(14), QTD_CARACTERES INT, QTD_DECIMAIS INT, IND_OBRIGATORIO VARCHAR(1))";
	public static final String QUERY_VALIDAR_BASE = "SELECT COUNT(1) FROM TB_MODELO";

	public static final String QUERY_SEL_MODELO = "SELECT ID, NOME, LABEL FROM TB_MODELO WHERE 1 = 1 ";
	public static final String QUERY_SEL_ATRIBUTO = "SELECT ID, ID_MODELO, NOME, LABEL, TIPO, QTD_CARACTERES, QTD_DECIMAIS, IND_OBRIGATORIO FROM TB_ATRIBUTO WHERE 1 = 1 ";
	public static final String QUERY_SEL_MODELO_NOME = "SELECT ID FROM TB_MODELO WHERE NOME = ";
	public static final String QUERY_INS_MODELO = "INSERT INTO TB_MODELO (ID, NOME, LABEL) VALUES (";
	public static final String QUERY_INS_ATRIBUTO = "INSERT INTO TB_ATRIBUTO (ID, ID_MODELO, NOME, LABEL, TIPO, QTD_CARACTERES, QTD_DECIMAIS, IND_OBRIGATORIO) VALUES (";
	public static final String QUERY_SELECT = "SELECT ";
	public static final String QUERY_FROM = " FROM ";
	public static final String QUERY_WHERE_ID = " WHERE ID = ";
	public static final String QUERY_INSERT = " INSERT INTO ";
	public static final String QUERY_UPDATE = " UPDATE ";
	public static final String QUERY_SET = " SET ";
	public static final String QUERY_DELETE = " DELETE FROM  ";

	public static final String QUERY_NEXT_VAL = "SELECT MAX(ID) + 1 AS NEXTID FROM ";
	public static final String QUERY_CREATE_TABLE = "CREATE TABLE ";
	public static final String QUERY_COL_ID = " ID INT PRIMARY KEY ";
	public static final String QUERY_VALUES = " VALUES ";

	// mensagens
	public static final String ERRO_DESCONHECIDO = "Ocorreu uma falha desconhecida.";
	public static final String WARN_CAMPOS_OBRIGATORIOS = "Favor preencher os seguintes campos";
	public static final String WARN_NOME_INVALIDO = "O campo Nome não pode conter espaços.";
	public static final String WARN_PRECISAO_INVALIDO = "O campo Qtd. Caracteres deve estar entre 1 e 500.";
	public static final String WARN_ESCALA_INVALIDO = "O campo Casas Decimais deve estar entre 0 e 10.";
	public static final String WARN_ATRIBUTO_OBRIGATORIO = "Insire ao menos um atributo, alem do ID.";
	public static final String WARN_ATRIBUTO_DUPLICADO = "Já existe um Atributo com este Nome.";
	public static final String WARN_MODELO_DUPLICADO = "Já existe um Modelo com este Nome.";
	public static final String WARN_MODELO_NAO_ENCONTRADO = "Modelo não encontrado.";
	public static final String WARN_ID_INVALIDO = "O ID informado deve ser numérico.";
	public static final String WARN_DADOS_OBRIG_EXCLUSAO = "Favor preencher os campos: Nome Modelo e ID.";

	// utils
	public static final String SEPARADOR_VIRGULA = " , ";
	public static final String ASPAS_SIMPLES = "'";
	public static final String PARENTESE_FECHA = " ) ";
	public static final String PARENTESE_ABRE = " ( ";
	public static final String ESPACO = " ";
	public static final String IGUAL = " = ";

}
