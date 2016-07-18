package poc.backend.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.exception.RepositoryException;
import poc.backend.exception.ServiceException;
import poc.backend.repository.AtributoRepository;
import poc.backend.repository.GenericRepository;
import poc.backend.repository.ModeloRepository;

/**
 * @author Jharbas Araujo
 */
@Service
public class ModeloService {

	private static Logger logger = Logger.getLogger(ModeloService.class.getName());

	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private AtributoRepository atributoRepository;

	@Autowired
	private GenericRepository genericRepository;

	/**
	 * pesquisa modelos na base de dados, filtrando por nome caso informado
	 * 
	 * @param dtoFiltro
	 * @return
	 * @throws ServiceException
	 */
	public List<ModeloDto> pesquisarModelo(ModeloDto dtoFiltro) throws ServiceException {
		try {
			return modeloRepository.pesquisarModelo(dtoFiltro);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * pesquisa o id do modelo, filtrando pelo nome
	 * 
	 * @param nome
	 * @return
	 * @throws ServiceException
	 */
	public Integer pesquisarIdPorNome(String nome) throws ServiceException {
		try {
			return modeloRepository.pesquisarIdPorNome(nome);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * pesquisa o modelo por id, preenchendo tambem a lista de atributos
	 * 
	 * @param idModelo
	 * @return
	 * @throws ServiceException
	 */
	public ModeloDto pesquisarModeloCompleto(Integer idModelo) throws ServiceException {
		try {
			ModeloDto modelo = modeloRepository.pesquisarModeloPorId(idModelo);
			modelo.setAtributos(atributoRepository.pesquisarAtributoPorModelo(idModelo));
			return modelo;
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}

	/**
	 * 
	 * @param dto
	 * @return
	 * @throws ServiceException
	 */
	public void salvarModelo(ModeloDto dto) throws ServiceException {
		try {
			dto = modeloRepository.salvarModelo(dto);

			for (AtributoDto atributoDto : dto.getAtributos()) {
				atributoDto.setIdModelo(dto.getId());
				atributoRepository.salvarAtributo(atributoDto);
			}

			genericRepository.criarTabela(dto);

		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e, logger);
		} catch (Exception e) {
			throw new ServiceException(Constantes.ERRO_DESCONHECIDO + " - " + e.getMessage(), logger);
		}
	}
}
