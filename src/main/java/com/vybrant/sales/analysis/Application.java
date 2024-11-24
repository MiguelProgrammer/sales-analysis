package com.vybrant.sales.analysis;

import com.vybrant.sales.analysis.service.DataAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

	@Autowired
	private static DataAnalysisService dataAnalysisService;

	public Application(DataAnalysisService dataAnalysisService) {
		Application.dataAnalysisService = dataAnalysisService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		try {

			log.info("Iniciando programa ...");
			dataAnalysisService.readFileFromResources("dados.txt");
		} catch (Exception e){
			log.error("Erro -> {}", e.getMessage());
		}
	}
}
