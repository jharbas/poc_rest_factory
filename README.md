# xy-inc

# Instalação:
 1. clone este repositório ``
 4. Execute a classe poc.backend.PocMain
 5. Acesso o front url http://localhost:9080/pages/index.jsf
 6. Para utlilizar os serviçoes Rest, utilize o contexto /backend
     Ex: http://localhost:9080/backend/SAPATO
     

## Arquitetura

# BackEnd
	* Spring Boot - http://projects.spring.io/spring-boot/
		- Embed Tomcat
		- Embed Apache Derby
		
		A necessidade de um ambiente auto configurável, para facilitar a entrega e validação do teste, levou a escolha desta arquitetura, que oferece um ambiente flexivel e dinamico.
		

# Frontend
	* Primefaces 5.0
		