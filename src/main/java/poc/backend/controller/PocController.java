package poc.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import poc.backend.constant.Constantes;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.exception.ServiceException;
import poc.backend.service.GenericService;

/**
 * @author Jharbas Araujo
 */
@RestController
@ResponseBody
@RequestMapping(value = "/backend")
public class PocController {

	private static Logger logger = Logger.getLogger(PocController.class.getName());

	@Autowired
	private GenericService genericService;

	/**
	 * Retorna todos os registros do modelo informado
	 * 
	 * @param modelo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{modelo}", method = RequestMethod.GET)
	public List<Map<String, Object>> buscarTodos(@PathVariable("modelo") String modelo) {
		List<ModeloDto> modelos = new ArrayList<ModeloDto>();
		try {
			logger.info("buscarTodos :: Inicio :: modelo - " + modelo);

			modelos = genericService.buscarTodos(modelo);

			logger.info("buscarTodos :: Fim");
		} catch (ServiceException e) {
			logger.severe("Erro: " + e.getMessage());
		}
		return getReturnList(modelos);
	}

	/**
	 * Busca por ID na tabela referente ao modelo informado
	 * 
	 * @param modelo
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{modelo}/{id}", method = RequestMethod.GET)
	public Map<String, Object> buscarPorId(@PathVariable("modelo") String modelo, @PathVariable("id") String id)
			throws ServiceException {
		logger.info("buscarPorId :: Inicio :: modelo - " + modelo);

		Integer idModelo = null;
		try {
			idModelo = Integer.parseInt(id);
		} catch (Exception e) {
			throw new IllegalArgumentException(Constantes.WARN_ID_INVALIDO);
		}

		ModeloDto modeloDto = genericService.buscarPorId(modelo, idModelo);

		logger.info("buscarPorId :: Fim");
		return getReturnMap(modeloDto);
	}

	@RequestMapping(value = "/{modelo}", method = RequestMethod.POST)
	public Map<String, Object> salvar(@PathVariable("modelo") String modelo, @RequestBody Map<String, Object> mapa)
			throws ServiceException {
		ModeloDto modeloDto = genericService.buscarModeloPorNome(modelo);

		if (mapa != null) {
			for (AtributoDto atributo : modeloDto.getAtributos()) {
				atributo.setValor(mapa.get(atributo.getNome()));
			}
		}

		modeloDto = genericService.salvar(modeloDto);
		return getReturnMap(modeloDto);
	}

	@RequestMapping(value = "/{modelo}/{id}", method = RequestMethod.PUT)
	public Map<String, Object> alterar(@PathVariable("modelo") String modelo, @PathVariable("id") String id,
			@RequestBody Map<String, Object> mapa) throws ServiceException {
		ModeloDto modeloDto = genericService.buscarModeloPorNome(modelo);

		Integer idModelo = null;
		try {
			idModelo = Integer.parseInt(id);
		} catch (Exception e) {
			throw new IllegalArgumentException(Constantes.WARN_ID_INVALIDO);
		}

		if (mapa != null) {
			for (AtributoDto atributo : modeloDto.getAtributos()) {
				atributo.setValor(mapa.get(atributo.getNome()));
			}
		}

		genericService.alterar(modeloDto, idModelo);
		modeloDto.setId(idModelo);
		return getReturnMap(modeloDto);
	}

	@RequestMapping(value = "/{modelo}/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> excluir(@PathVariable("modelo") String modelo, @PathVariable("id") String id)
			throws ServiceException {
		ModeloDto modeloDto = genericService.buscarModeloPorNome(modelo);

		Integer idModelo = null;
		try {
			idModelo = Integer.parseInt(id);
		} catch (Exception e) {
			throw new IllegalArgumentException(Constantes.WARN_ID_INVALIDO);
		}

		genericService.excluir(modelo, idModelo);
		return getReturnMap(modeloDto);
	}

	/**
	 * converte lista de ModeloDto em Lista de Map
	 * 
	 * @param modelos
	 * @return
	 */
	private List<Map<String, Object>> getReturnList(List<ModeloDto> modelos) {
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		for (ModeloDto modeloDto : modelos) {
			lista.add(getReturnMap(modeloDto));
		}
		return lista;
	}

	/**
	 * converte ModeloDto em Map
	 * 
	 * @param modelo
	 * @return
	 */
	private Map<String, Object> getReturnMap(ModeloDto modelo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelo != null && !CollectionUtils.isEmpty(modelo.getAtributos())) {
			for (AtributoDto atributoDto : modelo.getAtributos()) {
				map.put(atributoDto.getNome(), atributoDto.getValor());
			}
		}
		return map;
	}

}
