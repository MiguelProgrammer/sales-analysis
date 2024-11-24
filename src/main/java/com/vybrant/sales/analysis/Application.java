package com.vybrant.sales.analysis;

import com.vybrant.sales.analysis.service.DataAnalysisService;
import com.vybrant.sales.analysis.utils.AtributesFileHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.PathResource;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

			List<String> typesAceptFiles = Arrays.asList("txt");
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(dataAnalysisService.getCaminhoIn());

			WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);

			while(true){
				for(WatchEvent<?> event : watchKey.pollEvents()){

					log.info("Iniciando programa ...");
					Boolean isFile =  new PathResource(event.context().toString()).isFile();
					System.out.println("DESCRIPTION " + new PathResource(event.context().toString()).getDescription());
					String typeFile = AtributesFileHelper.getFileExtension(event.context().toString());

					if(typesAceptFiles.contains(typeFile) && isFile) {
						dataAnalysisService.readFileFromResources(event.context().toString());
					} else {
						log.error("File type not accpted!");
					}
				}
			}

		} catch (Exception e){
			log.error("Erro -> {}", e.getMessage());
		}
	}
}
