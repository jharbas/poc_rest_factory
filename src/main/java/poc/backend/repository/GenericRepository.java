package poc.backend.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.enuns.TipoAtributoEnum;
import poc.backend.exception.RepositoryException;

/**
 * @author Jharbas Araujo
 */
@Repository
public class GenericRepository extends DerbyJdbcManager {

	private static Logger logger = Logger.getLogger(GenericRepository.class.getName());

	public void criarTabela(ModeloDto dto) throws RepositoryException {
		try {

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_CREATE_TABLE);
			query.append(dto.getNome().toUpperCase().concat(Constantes.PARENTESE_ABRE));

			List<AtributoDto> atributos = dto.getAtributos();

			for (int i = 0; i < atributos.size(); i++) {
				AtributoDto atributo = atributos.get(i);

				// pk
				if (atributo.getNome().equalsIgnoreCase(Constantes.COL_ID)) {
					query.append(Constantes.QUERY_COL_ID);
				} else {
					query.append(atributo.getNome().toUpperCase().concat(Constantes.ESPACO));
					query.append(atributo.getTipo().getTipo().concat(Constantes.PARENTESE_ABRE));
					query.append(atributo.getPrecisao());

					if (atributo.getTipo().equals(TipoAtributoEnum.NUMERICO)) {
						query.append(Constantes.SEPARADOR_VIRGULA);
						query.append(atributo.getEscala());
					}

					query.append(Constantes.PARENTESE_FECHA);
				}

				if (i < (atributos.size() - 1)) {
					query.append(Constantes.SEPARADOR_VIRGULA);
				}
			}
			query.append(Constantes.PARENTESE_FECHA);

			executeUpdate(query);

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
	}

	/**
	 * Retorna todos os registros da tabela referente ao modelo informado
	 * 
	 * @param modeloDto
	 * @return
	 * @throws RepositoryException
	 */
	public List<ModeloDto> buscarTodos(ModeloDto modeloDto) throws RepositoryException {
		List<ModeloDto> lista = new ArrayList<ModeloDto>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SELECT);
			List<AtributoDto> atributos = modeloDto.getAtributos();
			for (int i = 0; i < atributos.size(); i++) {
				AtributoDto atributo = atributos.get(i);
				query.append(atributo.getNome());
				if (i < (atributos.size() - 1)) {
					query.append(Constantes.SEPARADOR_VIRGULA);
				}
			}
			query.append(Constantes.QUERY_FROM.concat(modeloDto.getNome()));

			ResultSet result = executeStringQuery(query);

			while (result.next()) {
				ModeloDto modeloResultado = new ModeloDto();
				modeloResultado.setAtributos(new ArrayList<AtributoDto>());

				for (AtributoDto atributoDto : atributos) {
					AtributoDto atributoResultado = new AtributoDto();

					atributoResultado.setNome(atributoDto.getNome());
					atributoResultado.setValor(result.getObject(atributoDto.getNome()));

					modeloResultado.getAtributos().add(atributoResultado);
				}
				lista.add(modeloResultado);
			}

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return lista;
	}

	/**
	 * Busca por ID na tabela referente ao modelo informado
	 * 
	 * @param modeloDto
	 * @param id
	 * @return
	 * @throws RepositoryException
	 */
	public ModeloDto buscarPorId(ModeloDto modeloDto, Integer id) throws RepositoryException {
		ModeloDto modeloResultado = new ModeloDto();
		try {
			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SELECT);
			List<AtributoDto> atributos = modeloDto.getAtributos();
			for (int i = 0; i < atributos.size(); i++) {
				AtributoDto atributo = atributos.get(i);
				query.append(atributo.getNome());
				if (i < (atributos.size() - 1)) {
					query.append(Constantes.SEPARADOR_VIRGULA);
				}
			}
			query.append(Constantes.QUERY_FROM.concat(modeloDto.getNome()));
			query.append(Constantes.QUERY_WHERE_ID);
			query.append(id);

			ResultSet result = executeStringQuery(query);

			if (result.next()) {

				modeloResultado.setAtributos(new ArrayList<AtributoDto>());

				for (AtributoDto atributoDto : atributos) {
					AtributoDto atributoResultado = new AtributoDto();

					atributoResultado.setNome(atributoDto.getNome());
					atributoResultado.setValor(result.getObject(atributoDto.getNome()));

					modeloResultado.getAtributos().add(atributoResultado);
				}
			}

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return modeloResultado;
	}

	/**
	 * salva o registro na tabela do modelo em questao
	 * 
	 * @param modeloDto
	 * @return
	 * @throws RepositoryException
	 */
	public ModeloDto salvar(ModeloDto modeloDto) throws RepositoryException {
		try {
			StringBuilder campos = new StringBuilder();
			campos.append(Constantes.PARENTESE_ABRE);

			StringBuilder valores = new StringBuilder();
			valores.append(Constantes.PARENTESE_ABRE);

			List<AtributoDto> atributos = modeloDto.getAtributos();
			for (int i = 0; i < atributos.size(); i++) {
				AtributoDto atributo = atributos.get(i);
				campos.append(atributo.getNome());

				if (atributo.getNome().equalsIgnoreCase(Constantes.COL_ID)) {
					Integer valorId = getNextVal(modeloDto.getNome());

					valores.append(valorId);
					atributo.setValor(valorId);
					modeloDto.getAtributos().set(i, atributo);

				} else if (atributo.getTipo().equals(TipoAtributoEnum.NUMERICO)) {
					valores.append(atributo.getValor());
				} else {
					if (atributo.getValor() != null) {
						valores.append(addAspasString(atributo.getValor().toString()));
					} else {
						valores.append(" null ");
					}
				}

				if (i < (atributos.size() - 1)) {
					campos.append(Constantes.SEPARADOR_VIRGULA);
					valores.append(Constantes.SEPARADOR_VIRGULA);
				}
			}
			campos.append(Constantes.PARENTESE_FECHA);
			valores.append(Constantes.PARENTESE_FECHA);

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_INSERT);
			query.append(modeloDto.getNome());
			query.append(campos);
			query.append(Constantes.QUERY_VALUES);
			query.append(valores);

			executeUpdate(query);

			return modeloDto;

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
	}

	/**
	 * atualiza o registro na tabela do modelo informado
	 * 
	 * @param modeloDto
	 * @return
	 * @throws RepositoryException
	 */
	public void alterar(ModeloDto modeloDto, Integer id) throws RepositoryException {
		try {
			StringBuilder query = new StringBuilder();

			query.append(Constantes.QUERY_UPDATE);
			query.append(modeloDto.getNome());
			query.append(Constantes.QUERY_SET);

			List<AtributoDto> atributos = modeloDto.getAtributos();

			for (int i = 0; i < atributos.size(); i++) {
				AtributoDto atributo = atributos.get(i);

				if (!atributo.getNome().equalsIgnoreCase(Constantes.COL_ID)) {

					query.append(atributo.getNome());
					query.append(Constantes.IGUAL);

					if (atributo.getTipo().equals(TipoAtributoEnum.NUMERICO)) {
						query.append(atributo.getValor());
					} else {
						if (atributo.getValor() != null) {
							query.append(addAspasString(atributo.getValor().toString()));
						} else {
							query.append(" null ");
						}
					}
					if (i < (atributos.size() - 1)) {
						query.append(Constantes.SEPARADOR_VIRGULA);
					}
				}
			}

			query.append(Constantes.QUERY_WHERE_ID);
			query.append(id);

			executeUpdate(query);

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}

	}

	/**
	 * exclui o registro da base, conforme id e nomeModelo informados
	 * 
	 * @param nomeModelo
	 * @param id
	 * @throws RepositoryException
	 */
	public void excluir(String nomeModelo, Integer id) throws RepositoryException {
		try {
			StringBuilder query = new StringBuilder();

			query.append(Constantes.QUERY_DELETE);
			query.append(nomeModelo);
			query.append(Constantes.QUERY_WHERE_ID);
			query.append(id);
			executeUpdate(query);

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
	}

}
