package poc.backend.service;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.exception.ServiceException;
import poc.backend.repository.GenericRepository;

/**
 * @author Jharbas Araujo
 */
@Service
public class GenericService {

	@Autowired
	ModeloService modeloService;

	@Autowired
	GenericRepository genericRepository;

	private static Logger logger = Logger.getLogger(GenericService.class.getName());

	/**
	 * Retorna todos os registros da tabela referente ao modelo informado
	 * 
	 * @param nomeModelo
	 * @return
	 * @throws ServiceException
	 */
	public List<ModeloDto> buscarTodos(String nomeModelo) throws ServiceException {
		try {
			// verfica a existencia do modelo e recupera o id
			Integer codigoModelo = modeloService.pesquisarIdPorNome(nomeModelo.toUpperCase());
			if (codigoModelo == null) {
				throw new ServiceException(Constantes.WARN_MODELO_NAO_ENCONTRADO, logger);
			}

			// busca modelo completo
			ModeloDto modeloDto = modeloService.pesquisarModeloCompleto(codigoModelo);

			// busca registros do modelo
			return genericRepository.buscarTodos(modeloDto);

		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * Busca por ID na tabela referente ao modelo informado
	 * 
	 * @param nomeModelo
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ModeloDto buscarPorId(String nomeModelo, Integer id) throws ServiceException {
		try {
			// busca modelo completo
			ModeloDto modeloDto = buscarModeloPorNome(nomeModelo.toUpperCase());

			// busca registros do modelo
			return genericRepository.buscarPorId(modeloDto, id);

		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * valida os dados fornecidos e salva o registro na tabela do modelo em
	 * questao
	 * 
	 * @param modeloDto
	 * @return
	 * @throws ServiceException
	 */
	public ModeloDto salvar(ModeloDto modeloDto) throws ServiceException {
		try {
			if (validarCampos(modeloDto)) {
				return genericRepository.salvar(modeloDto);
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
		return null;
	}

	/**
	 * altera o registro na tabela do modelo em questao
	 * 
	 * @param modeloDto
	 * @throws ServiceException
	 */
	public void alterar(ModeloDto modeloDto, Integer id) throws ServiceException {
		try {
			if (validarCampos(modeloDto)) {
				genericRepository.alterar(modeloDto, id);
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * excluir o registro na tabela do modelo em questao
	 * 
	 * @param modeloDto
	 * @throws ServiceException
	 */
	public void excluir(String nomeModelo, Integer id) throws ServiceException {
		try {
			if (StringUtils.isNotBlank(nomeModelo) && id != null) {
				genericRepository.excluir(nomeModelo, id);
			} else {
				throw new ServiceException(Constantes.WARN_DADOS_OBRIG_EXCLUSAO, logger);
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * verifica se existe um modelo com um determinado nome, e recupera o id do
	 * mesmo
	 * 
	 * @param nomeModelo
	 * @return
	 * @throws ServiceException
	 */
	public ModeloDto buscarModeloPorNome(String nomeModelo) throws ServiceException {
		try {

			// verfica a existencia do modelo e recupera o id
			Integer codigoModelo = modeloService.pesquisarIdPorNome(nomeModelo.toUpperCase());
			if (codigoModelo == null) {
				throw new ServiceException(Constantes.WARN_MODELO_NAO_ENCONTRADO, logger);
			}

			// busca modelo completo
			return modeloService.pesquisarModeloCompleto(codigoModelo);

		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	private boolean validarCampos(ModeloDto modeloDto) throws ServiceException {
		if (modeloDto != null && !CollectionUtils.isEmpty(modeloDto.getAtributos())) {
			StringBuilder camposNaoPreenchidos = new StringBuilder();

			for (AtributoDto atributoDto : modeloDto.getAtributos()) {

				if (!atributoDto.getNome().equalsIgnoreCase(Constantes.COL_ID)
						&& atributoDto.getIndObrigatorio().equals(Constantes.SIM) && atributoDto.getValor() == null) {
					camposNaoPreenchidos.append(" - ".concat(atributoDto.getNome()));
				}

			}

			if (camposNaoPreenchidos.length() > 0) {
				throw new ServiceException(Constantes.WARN_CAMPOS_OBRIGATORIOS.concat(camposNaoPreenchidos.toString()),
						logger);
			}
		}
		return true;

	}

}
