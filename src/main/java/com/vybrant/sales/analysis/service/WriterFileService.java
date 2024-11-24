package com.vybrant.sales.analysis.service;

import com.vybrant.sales.analysis.domain.Client;
import com.vybrant.sales.analysis.domain.Sale;
import com.vybrant.sales.analysis.domain.Salesman;
import com.vybrant.sales.analysis.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WriterFileService {

    @Autowired
    private SaleRepository saleRepository;

    /**
     * REALIZA A ESCRITA EM ARQUIVO
     * REALIZR A CRIAÇÃO DO RELATÓRIO
     *
     * @param caminhoOut
     * @param caminhoIn
     * @param clientList
     * @param salesmanList
     */
    public void writeDataOut(String caminhoOut, String caminhoIn, List<Client> clientList, List<Salesman> salesmanList) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoOut, true))) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String styleDoc = "--------------------------------- RELATÓRIO "+ formato.format(new Date()) +" --------------------";
            writeInFile(bw, styleDoc);

            String nomeArquivo = "* Arquivo processado = " + new PathResource(caminhoIn).getFilename();
            writeInFile(bw, nomeArquivo);

            String smsCliente = "* Quantidade de clientes no arquivo de entrada = " + clientList.size();
            writeInFile(bw, smsCliente);

            String smsVendedor = "* Quantidade de vendedores no arquivo de entrada = " + salesmanList.size();
            writeInFile(bw, smsVendedor);

            Sale saleMaxSale = saleRepository.findTopByOrderByTotalDesc().get();
            String idVendaMaiorValor = "* ID da venda mais cara = " + saleMaxSale.getIdSale() + " | Valor R$ " + saleMaxSale.getTotal();
            writeInFile(bw, idVendaMaiorValor);

            Sale saleMinSale = saleRepository.findFirstByOrderByTotalAsc().get();
            String idVendaMenorValor = "* O pior vendedor, que vendeu o menor valor acumulado = " +
                    saleMinSale.getIdSale() + " | Valor R$ " + saleMinSale.getTotal();
            writeInFile(bw, idVendaMenorValor);

            writeInFile(bw, styleDoc);
            bw.close();

            Files.createFile(Paths.get(caminhoOut));

        } catch (IOException e) {
            //log.error("Erro -> {}", e.getMessage());
        }
    }


    private void writeInFile(BufferedWriter bw, String dados) {
        try {
            bw.write(dados);
            bw.newLine();
        } catch (Exception e) {
            log.error("Erro -> {}", e.getMessage());
        }
    }
}
