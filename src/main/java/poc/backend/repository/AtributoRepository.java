package poc.backend.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.enuns.TipoAtributoEnum;
import poc.backend.exception.RepositoryException;

/**
 * @author Jharbas Araujo
 */
@Repository
public class AtributoRepository extends DerbyJdbcManager {

	private static Logger logger = Logger.getLogger(AtributoRepository.class.getName());

	/**
	 * retorna lista de atributos filtrando pelo modelo
	 * 
	 * @param idModelo
	 * @return
	 * @throws RepositoryException
	 */
	public List<AtributoDto> pesquisarAtributoPorModelo(Integer idModelo) throws RepositoryException {
		List<AtributoDto> lista = new ArrayList<AtributoDto>();
		try {

			logger.info("pesquisarAtributoPorModelo :: Inicio");

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_SEL_ATRIBUTO);
			query.append(addParamInteger(Constantes.COL_ID_MODELO, idModelo));
			ResultSet result = executeStringQuery(query);

			while (result.next()) {
				AtributoDto atributoDto = new AtributoDto();

				atributoDto.setId(result.getInt(Constantes.COL_ID));
				atributoDto.setIdModelo(result.getInt(Constantes.COL_ID_MODELO));
				atributoDto.setNome(result.getString(Constantes.COL_NOME));
				atributoDto.setLabel(result.getString(Constantes.COL_LABEL));

				String tipo = result.getString(Constantes.COL_TIPO);
				if (StringUtils.isNotBlank(tipo)) {
					atributoDto.setTipo(TipoAtributoEnum.valueOf(tipo));
				}

				atributoDto.setPrecisao(result.getInt(Constantes.COL_QTD_CARACTERES));
				atributoDto.setEscala(result.getInt(Constantes.COL_QTD_DECIMAIS));
				atributoDto.setIndObrigatorio(result.getString(Constantes.COL_IND_OBRIGATORIO));

				lista.add(atributoDto);
			}
			logger.info("pesquisarAtributoPorModelo :: Fim");

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
		return lista;
	}

	public void salvarAtributo(AtributoDto dto) throws RepositoryException {
		try {
			dto.setId(getNextVal(Constantes.TB_ATRIBUTO));

			StringBuilder query = new StringBuilder();
			query.append(Constantes.QUERY_INS_ATRIBUTO);
			query.append(dto.getId());
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(dto.getIdModelo());
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(dto.getNome().toUpperCase()));
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(dto.getLabel()));
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(dto.getTipo().toString()));
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(dto.getPrecisao());
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(dto.getEscala());
			query.append(Constantes.SEPARADOR_VIRGULA);
			query.append(addAspasString(dto.getIndObrigatorio()));
			query.append(Constantes.PARENTESE_FECHA);

			executeUpdate(query);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e, logger);
		}
	}

}
