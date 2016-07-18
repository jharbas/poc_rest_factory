package poc.backend.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import poc.backend.constant.Constantes;

/**
 * @author Jharbas Araujo
 */
public class DerbyJdbcManager {

	private static Logger logger = Logger.getLogger(DerbyJdbcManager.class.getName());

	private Connection connection;

	public DerbyJdbcManager() {
		try {
			logger.info("Criando a conneccao com a base de dados");
			String workingDir = System.getProperty("user.dir");
			String dbUrl = "jdbc:derby:" + workingDir + "/db;create=true";
			connection = DriverManager.getConnection(dbUrl);
		} catch (Exception e) {
			logger.severe("Erro ao criar a base de dados: " + e.getMessage());
		}
	}

	/**
	 * valida se ja existe uma base de dados do projeto, se nao, cria a base
	 * inicial
	 */
	public void createDB() {
		try {
			if (!checkExistingBase()) {

				logger.info("Criando a base de dados.");

				logger.info("Criando tabela de modelo");
				getStatement().executeUpdate(Constantes.QUERY_CRIAR_TB_MODELO);

				logger.info("Criando tabela de atributos");
				getStatement().executeUpdate(Constantes.QUERY_CRIAR_TB_ATRIBUTO);

				// getStatement().executeUpdate("insert into TB_MODELO values
				// (1,'Carro', 'CARRO')");
				// getStatement().executeUpdate("insert into TB_MODELO values
				// (2,'Moto', 'MOTO')");

			}

		} catch (Exception e) {
			logger.severe("Erro ao criar a base de dados: " + e.getMessage());
		}
	}

	private boolean checkExistingBase() {
		try {
			logger.info("Verificando a existencia da base de dados.");

			@SuppressWarnings("unused")
			ResultSet rs = getStatement().executeQuery(Constantes.QUERY_VALIDAR_BASE);

			return Boolean.TRUE;

		} catch (Exception e) {
			logger.info("Base não existe ainda");
			return Boolean.FALSE;
		}
	}

	/**
	 * cria um Statement, utilizando a conneccao do repositorio
	 * 
	 * @return
	 */
	protected Statement getStatement() {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			logger.severe("Erro ao criar statement: " + e.getMessage());
		}
		return null;

	}

	/**
	 * executa a query fornecida e retorna o resultSet para ser tratado pelo
	 * repositorio
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet executeStringQuery(StringBuilder query) throws SQLException {
		return getStatement().executeQuery(query.toString());
	}

	/**
	 * executa a query de insert ou update
	 * 
	 * @param query
	 * @throws SQLException
	 */
	protected void executeUpdate(StringBuilder query) throws SQLException {
		getStatement().executeUpdate(query.toString());
	}

	/**
	 * retorna a proxima chave da tabela
	 * 
	 * @param nomeTabela
	 * @return
	 * @throws SQLException
	 */
	protected Integer getNextVal(String nomeTabela) throws SQLException {
		ResultSet rs = getStatement().executeQuery(Constantes.QUERY_NEXT_VAL.concat(nomeTabela));
		rs.next();
		return rs.getInt(Constantes.COL_NEXTID);
	}

	/**
	 * retorna uma string com o filtro a ser adicionado na query
	 * 
	 * @param coluna
	 * @param valor
	 * @return
	 */
	protected String addParamString(String coluna, String valor) {
		if (StringUtils.isNotBlank(coluna) && StringUtils.isNotBlank(valor)) {
			return " AND ".concat(coluna).concat(" like '").concat(valor).concat("%' ");
		}
		return StringUtils.EMPTY;
	}

	/**
	 * retorna uma string com o filtro a ser adicionado na query
	 * 
	 * @param coluna
	 * @param valor
	 * @return
	 */
	protected String addParamStringEq(String coluna, String valor) {
		if (StringUtils.isNotBlank(coluna) && StringUtils.isNotBlank(valor)) {
			return " AND ".concat(coluna).concat(" = '").concat(valor).concat("' ");
		}
		return StringUtils.EMPTY;
	}

	/**
	 * retorna uma string com o filtro a ser adicionado na query
	 * 
	 * @param coluna
	 * @param valor
	 * @return
	 */
	protected String addParamInteger(String coluna, Integer valor) {
		if (StringUtils.isNotBlank(coluna) && valor != null) {
			return " AND ".concat(coluna).concat(" = ").concat(String.valueOf(valor)).concat(" ");
		}
		return StringUtils.EMPTY;
	}

	/**
	 * retorna uma string com o filtro a ser adicionado na query
	 * 
	 * @param valor
	 * @return
	 */
	protected String addParamId(Integer valor) {
		if (valor != null) {
			return (" AND ID = ").concat(String.valueOf(valor)).concat(" ");
		}
		return StringUtils.EMPTY;
	}

	protected String addAspasString(String valor) {
		return Constantes.ASPAS_SIMPLES.concat(valor).concat(Constantes.ASPAS_SIMPLES);
	}

}
