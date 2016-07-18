package poc.backend.config;

import javax.annotation.PostConstruct;
import javax.faces.webapp.FacesServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import poc.backend.repository.ModeloRepository;

/**
 * @author Jharbas Araujo
 */
@Configuration
public class AppConfig {

	@Autowired
	ModeloRepository repository;

	@PostConstruct
	public void configuration() {
		repository.createDB();
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
		return servletRegistrationBean;
	}

}
