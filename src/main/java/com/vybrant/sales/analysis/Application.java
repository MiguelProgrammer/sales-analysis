package com.vybrant.sales.analysis;

import com.vybrant.sales.analysis.service.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

			dataAnalysisService.readFileFromResources("dados.txt");

		} catch (Exception e){
			System.err.println(e);
		}
	}
}
