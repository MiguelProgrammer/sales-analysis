package com.vybrant.sales.analysis.service;


import com.vybrant.sales.analysis.domain.Client;
import com.vybrant.sales.analysis.domain.Item;
import com.vybrant.sales.analysis.domain.Sale;
import com.vybrant.sales.analysis.domain.Salesman;
import com.vybrant.sales.analysis.repository.ClientRepository;
import com.vybrant.sales.analysis.repository.ItemRepository;
import com.vybrant.sales.analysis.repository.SaleRepository;
import com.vybrant.sales.analysis.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class DataAnalysisService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ItemRepository itemRepository;

    private final List<Client> clientList = new ArrayList<>();
    private final List<Salesman> salesmanList = new ArrayList<>();
    private final Set<Sale> saleList = new HashSet<>();


    public DataAnalysisService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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

                String idSalesman = dados[1];
                String name = dados[2];
                BigDecimal value = new BigDecimal(dados[3]);
                Salesman salesman = new Salesman(idSalesman, name, value);

                salesmanList.add(salesmanRepository.save(salesman));
                return;
            }
            if (dados[0].equals("002")) {

                String idClient = dados[1];
                String name = dados[2];
                Client client = new Client(idClient, name);

                clientList.add(clientRepository.save(client));
                return;
            }
            if (dados[0].equals("003")) {

                Sale sale = new Sale();
                String[] dataItem = dados[2].split(",");
                String dadosItem1 = dataItem[0].replaceAll("\\[", "");
                String dadosItem2 = dataItem[1];
                String dadosItem3 = dataItem[2].replaceAll("]", "");

                Set<Item> itemList = new HashSet<>();
                List<String> dadosItem = Arrays.asList(dadosItem1, dadosItem2, dadosItem3);

                dadosItem.stream().forEach(it -> {

                    String[] itemSplited = it.split("-");

                    Long idItem = Long.parseLong(itemSplited[0]);
                    Integer quantity = Integer.parseInt(itemSplited[1]);
                    BigDecimal price = new BigDecimal(itemSplited[2]);

                    Item item = new Item(idItem, quantity, price);
                    Item save = itemRepository.save(item);
                    itemList.add(save);

                });

                Optional<Salesman> salesmanByIdName = salesmanRepository.findByName(dados[3]);
                if (salesmanByIdName.isPresent()) {

                    Salesman salesman = salesmanByIdName.get();

                    String idSale = dados[1];
                    sale = new Sale(idSale, itemList, salesman);
                    Sale save = saleRepository.save(sale);

                    /**
                     * UPDATE SALE LIST SALESMAN
                     */
                    saleList.add(save);
                    salesman.setSale(sale);
                    salesmanRepository.saveAndFlush(salesman);
                }

            }
        }
    }

}
