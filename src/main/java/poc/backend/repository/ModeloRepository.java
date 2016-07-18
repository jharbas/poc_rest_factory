package poc.backend.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.exception.RepositoryException;

/**
 * @author Jharbas Araujo
 */
@Repository
public class ModeloRepository extends DerbyJdbcManager {

	private static Logger logger = Logger.getLogger(ModeloRepository.class.getName());

	/**
	 * pesquisa modelos na base de dados, filtrando por nome caso informado
	 * 
	 * @param dtoFiltro
	 * @return
	 * @throws RepositoryException
	 */
	public List<ModeloDto> pesquisarModelo(ModeloDto dtoFiltro) throws RepositoryException {
		List<ModeloDto> lista = new ArrayList<>();
		try {

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SEL_MODELO);
			query.append(addParamString(Constantes.COL_NOME, dtoFiltro.getNome()));
			query.append(addParamString(Constantes.COL_LABEL, dtoFiltro.getLabel()));
			ResultSet result = executeStringQuery(query);

			while (result.next()) {
				ModeloDto dto = new ModeloDto();

				dto.setId(result.getInt(Constantes.COL_ID));
				dto.setNome(result.getString(Constantes.COL_NOME));
				dto.setLabel(result.getString(Constantes.COL_LABEL));

				lista.add(dto);
			}

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return lista;
	}

	/**
	 * pesquisa o id do modelo, filtrando pelo nome
	 * 
	 * @param nome
	 * @return
	 * @throws RepositoryException
	 */
	public Integer pesquisarIdPorNome(String nome) throws RepositoryException {
		try {
			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SEL_MODELO_NOME);
			query.append(addAspasString(nome));

			ResultSet result = executeStringQuery(query);
			if (result.next()) {
				return result.getInt(Constantes.COL_ID);
			}

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return null;
	}

	/**
	 * Pesquisa a o modelo por id
	 * 
	 * @param dtoFiltro
	 * @return
	 * @throws RepositoryException
	 */
	public ModeloDto pesquisarModeloPorId(Integer id) throws RepositoryException {
		ModeloDto dtoResultado = new ModeloDto();
		dtoResultado.setAtributos(new ArrayList<AtributoDto>());
		try {

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SEL_MODELO);
			query.append(addParamId(id));
			ResultSet resultModelo = executeStringQuery(query);

			resultModelo.next();
			dtoResultado.setId(resultModelo.getInt(Constantes.COL_ID));
			dtoResultado.setNome(resultModelo.getString(Constantes.COL_NOME));
			dtoResultado.setLabel(resultModelo.getString(Constantes.COL_LABEL));

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return dtoResultado;
	}

	/**
	 * Salva o modelo e seus atributos na base de dados
	 * 
	 * @param modeloDto
	 * @return
	 * @throws RepositoryException
	 */
	public ModeloDto salvarModelo(ModeloDto modeloDto) throws RepositoryException {
		try {
			modeloDto.setId(getNextVal(Constantes.TB_MODELO));

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_INS_MODELO);
			query.append(modeloDto.getId());
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(modeloDto.getNome().toUpperCase()));
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(modeloDto.getLabel()));
			query.append(Constantes.PARENTESE_FECHA);

			executeUpdate(query);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return modeloDto;
	}
}
