package poc.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import poc.backend.constant.Constantes;
import poc.backend.controller.PocController;
import poc.backend.dto.AtributoDto;
import poc.backend.dto.ModeloDto;
import poc.backend.enuns.TipoAtributoEnum;
import poc.backend.exception.ServiceException;
import poc.backend.service.GenericService;
import poc.backend.service.ModeloService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class PocTests {

	private static Logger logger = Logger.getLogger(PocTests.class.getName());

	private static final String MODELO = "CENTROCUSTO";

	@Autowired
	private PocController pocController;

	@Autowired
	private ModeloService modeloService;

	@Autowired
	private GenericService genericService;

	@Before
	public void configurarModeloTeste() {
		prepararMockTestes();
	}

	private void prepararMockTestes() {
		try {

			Integer idModelo = modeloService.pesquisarIdPorNome(MODELO);

			if (idModelo == null) {
				ModeloDto modeloDto = new ModeloDto();
				modeloDto.setNome(MODELO);
				modeloDto.setLabel("Centro de Custo");

				List<AtributoDto> atributos = new ArrayList<AtributoDto>();

				AtributoDto atrId = new AtributoDto();
				atrId.setLabel("Id");
				atrId.setNome("ID");
				atrId.setTipo(TipoAtributoEnum.NUMERICO);
				atrId.setPrecisao(10);
				atrId.setEscala(0);
				atrId.setIndObrigatorio(Constantes.SIM);
				atrId.setIndPk(Constantes.SIM);
				atributos.add(atrId);

				AtributoDto atrNome = new AtributoDto();
				atrNome.setLabel("Nome");
				atrNome.setNome("NOME");
				atrNome.setTipo(TipoAtributoEnum.ALFANUMERICO);
				atrNome.setPrecisao(20);
				atrNome.setIndObrigatorio(Constantes.SIM);
				atrNome.setIndPk(Constantes.NAO);
				atributos.add(atrNome);

				AtributoDto atrIdentificador = new AtributoDto();
				atrIdentificador.setLabel("Identificador");
				atrIdentificador.setNome("IDENTIFICADOR");
				atrIdentificador.setTipo(TipoAtributoEnum.NUMERICO);
				atrIdentificador.setPrecisao(20);
				atrIdentificador.setEscala(0);
				atrIdentificador.setIndObrigatorio(Constantes.SIM);
				atrIdentificador.setIndPk(Constantes.NAO);
				atributos.add(atrIdentificador);

				modeloDto.setAtributos(atributos);

				modeloService.salvarModelo(modeloDto);
			}

		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Test
	public void salvarInstanciaModelo() throws ServiceException {

		ModeloDto modeloDto = new ModeloDto();
		modeloDto.setNome(MODELO);

		List<AtributoDto> atributos = new ArrayList<AtributoDto>();

		AtributoDto atrId = new AtributoDto();
		atrId.setLabel("Id");
		atrId.setNome("ID");
		atrId.setTipo(TipoAtributoEnum.NUMERICO);
		atrId.setPrecisao(10);
		atrId.setEscala(0);
		atrId.setIndObrigatorio(Constantes.SIM);
		atrId.setIndPk(Constantes.SIM);
		atributos.add(atrId);

		AtributoDto atrNome = new AtributoDto();
		atrNome.setLabel("Nome");
		atrNome.setNome("NOME");
		atrNome.setTipo(TipoAtributoEnum.ALFANUMERICO);
		atrNome.setPrecisao(20);
		atrNome.setIndObrigatorio(Constantes.SIM);
		atrNome.setIndPk(Constantes.NAO);
		atrNome.setValor("CENTRO1");
		atributos.add(atrNome);

		AtributoDto atrIdentificador = new AtributoDto();
		atrIdentificador.setLabel("Identificador");
		atrIdentificador.setNome("IDENTIFICADOR");
		atrIdentificador.setTipo(TipoAtributoEnum.NUMERICO);
		atrIdentificador.setPrecisao(20);
		atrIdentificador.setEscala(0);
		atrIdentificador.setIndObrigatorio(Constantes.SIM);
		atrIdentificador.setIndPk(Constantes.NAO);
		atrIdentificador.setValor(234);
		atributos.add(atrIdentificador);
		modeloDto.setAtributos(atributos);

		modeloDto = genericService.salvar(modeloDto);

		Assert.assertNotNull(modeloDto);
	}

	@Test
	public void buscarTodosModeloNaoEncontrado() {
		List<Map<String, Object>> lista = pocController.buscarTodos(MODELO.concat("TESTE1"));
		Assert.assertEquals(0, lista.size());
	}

	@Test
	public void buscarTodos() {
		List<Map<String, Object>> lista = pocController.buscarTodos(MODELO);
		Assert.assertNotNull(lista);

	}

	@Test
	public void buscarPorId() throws ServiceException {
		Map<String, Object> mapa = pocController.buscarPorId(MODELO, "0");
		Assert.assertNotNull(mapa);
	}

	@Test
	public void salvar() throws ServiceException {

		Map<String, Object> mapa = new HashMap<String, Object>();

		mapa.put("NOME", "Financeiro");
		mapa.put("IDENTIFICADOR", 7463);
		mapa = pocController.salvar(MODELO, mapa);
		Assert.assertNotNull(mapa);

	}

	@Test
	public void alterar() throws ServiceException {

		Map<String, Object> mapa = new HashMap<String, Object>();

		mapa.put("NOME", "Financeiro2");
		mapa.put("IDENTIFICADOR", 7463);
		pocController.alterar(MODELO, "0", mapa);
	}

	public void excluir() throws ServiceException {
		pocController.excluir(MODELO, "0");
	}

}
