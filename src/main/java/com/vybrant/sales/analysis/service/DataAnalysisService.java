package com.vybrant.sales.analysis.service;

import com.vybrant.sales.analysis.domain.Client;
import com.vybrant.sales.analysis.domain.Sale;
import com.vybrant.sales.analysis.domain.Salesman;
import com.vybrant.sales.analysis.repository.ClientRepository;
import com.vybrant.sales.analysis.repository.SaleRepository;
import com.vybrant.sales.analysis.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DataAnalysisService {

    @Autowired
    private ResourceLoader resourceLoader;

    private List<Client> clientList = new ArrayList<>();
    private List<Salesman> salesmanList = new ArrayList<>();
    private List<Sale> saleList = new ArrayList<>();


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private SaleRepository saleRepository;

    public void readFileFromResources(String filename) throws IOException {

        String caminhoIn = "C:\\dev\\data\\in\\" + filename;
        String caminhoOut = "C:\\dev\\data\\out\\" + filename;

        readDataIn(caminhoIn);
        Files.deleteIfExists(Path.of(caminhoOut));
        writeDataOut(caminhoOut, caminhoIn);
    }

    private void readDataIn(String caminhoIn) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoIn))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                this.preparedData(linha);
            }
        }
    }

    private void writeDataOut(String caminhoOut, String caminhoIn) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoOut, true))) {

            String smsCliente = "Quantidade de clientes no arquivo de entrada = " + clientList.size();
            String smsVendedor = "Quantidade de vendedores no arquivo de entrada = " + salesmanList.size();

            bw.write(smsCliente);
            bw.newLine();
            bw.write(smsVendedor);
            bw.newLine();
            bw.close();

            Files.createFile(Paths.get(caminhoOut));

        } catch (IOException e) {
            //System.out.println("Erro " + e.getMessage());
        }
    }

    private void preparedData(String linha) {
        String[] dados = linha.split("ç");

        if (Objects.nonNull(dados[0])) {
            if (dados[0].equals("001")) {
                salesmanList.add(salesmanRepository.save(new Salesman().CreateSalesman(dados)));
            }
            if (dados[0].equals("002")) {
                clientList.add(clientRepository.save(new Client().CreateClient(dados)));
            }
            if (dados[0].equals("003")) {
                saleList.add(saleRepository.save(new Sale().CreateSale(dados)));
            }
        }
    }

}
