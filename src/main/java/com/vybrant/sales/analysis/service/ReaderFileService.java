package com.vybrant.sales.analysis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Slf4j
@Service
public class ReaderFileService {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    /**
     * LEITURA DO ARQUIVO
     *
     * @param caminhoIn
     */
    public void readDataIn(String caminhoIn) {

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoIn))) {

            String linha;
            log.info("Processamento de arquivo iniciado ...");
            while ((linha = br.readLine()) != null) {
                dataAnalysisService.preparedData(linha);
            }
            log.info("Processamento de arquivo finalizado ...");

        } catch (Exception e) {
            log.error("Erro -> {}", e.getMessage());
        }
    }
}
