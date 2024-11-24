package com.vybrant.sales.analysis.service;


import com.vybrant.sales.analysis.domain.Client;
import com.vybrant.sales.analysis.domain.Item;
import com.vybrant.sales.analysis.domain.Sale;
import com.vybrant.sales.analysis.domain.Salesman;
import com.vybrant.sales.analysis.repository.ClientRepository;
import com.vybrant.sales.analysis.repository.ItemRepository;
import com.vybrant.sales.analysis.repository.SaleRepository;
import com.vybrant.sales.analysis.repository.SalesmanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class DataAnalysisService {

    @Value("${caminho.entrada.arquivo}")
    private String caminhoIn;

    @Value("${caminho.saida.arquivo}")
    private String caminhoOut;

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

    @Autowired
    private WriterFileService writeInFile;

    @Autowired
    private ReaderFileService readerFileService;

    private final List<Client> clientList = new ArrayList<>();
    private final List<Salesman> salesmanList = new ArrayList<>();
    private final Set<Sale> saleList = new HashSet<>();


    public DataAnalysisService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * LEITURA DO ARQUIVO
     * PROCESSAMENTO D ARQUIVO
     * PERSISTENCIA EM BASE
     * ESCRITA EM ARQUIVO
     *
     * @param filename
     * @throws IOException
     */
    public void readFileFromResources(String filename) throws IOException {

        caminhoIn = caminhoIn.concat(filename);
        caminhoOut = caminhoOut.concat(filename);

        log.info("Leitura do arquivo de entrada iniciada ...");
        readerFileService.readDataIn(caminhoIn);

        Files.deleteIfExists(Path.of(caminhoOut));

        log.info("Escrita do arquivo de saída iniciada ...");
        writeInFile.writeDataOut(caminhoOut, caminhoIn, clientList, salesmanList);

    }



    /**
     * SPLIT DADOS DO ARQUIVO PARA SEUS RESPECTIVOS DOMINIOS
     * VENDEDOR, CLIENTE E VENDAS
     *
     * @param linha
     */
    public void preparedData(String linha) {

        String[] dados = linha.split("ç");

        if (Objects.nonNull(dados[0])) {

            switch (dados[0]) {

                case "001" -> {

                    String idSalesman = dados[1];
                    String name = dados[2];
                    BigDecimal value = new BigDecimal(dados[3]);
                    Salesman salesman = new Salesman(idSalesman, name, value);
                    salesmanList.add(salesmanRepository.save(salesman));
                }

                case "002" -> {

                    String idClient = dados[1];
                    String name = dados[2];
                    Client client = new Client(idClient, name);

                    clientList.add(clientRepository.save(client));
                }

                case "003" -> {

                    AtomicReference<BigDecimal> valorTotalCompra =
                            new AtomicReference<>(new BigDecimal(String.valueOf(BigDecimal.ZERO)));
                    Set<Item> itemList = new HashSet<>();
                    List<String> dadosItem = preparedSaleData(dados[2]);


                    dadosItem.forEach(it -> {

                        String[] itemSplited = it.split("-");

                        Long idItem = Long.parseLong(itemSplited[0]);
                        Integer quantity = Integer.parseInt(itemSplited[1]);
                        BigDecimal price = new BigDecimal(itemSplited[2]);

                        valorTotalCompra.updateAndGet(v -> v.add(price));

                        Item item = new Item(idItem, quantity, price);
                        Item save = itemRepository.save(item);

                        itemList.add(save);

                    });

                    Optional<Salesman> salesmanByIdName = salesmanRepository.findByName(dados[3]);

                    if (salesmanByIdName.isPresent()) {

                        Salesman salesman = salesmanByIdName.get();

                        String idSale = dados[1];
                        Sale sale = new Sale(idSale, itemList, salesman, valorTotalCompra.get());
                        Sale save = saleRepository.save(sale);

                        /**
                         * ATUALIZA VENDA E VENDEDOR
                         */
                        saleList.add(save);
                        salesman.setSale(sale);
                        salesmanRepository.saveAndFlush(salesman);
                    }
                }
            }

        }
    }

    /**
     * PREPARA DADOS DAS VENDAS
     *
     * @param dados
     * @return
     */
    private List<String> preparedSaleData(String dados) {

        String[] dataItem = dados.split(",");

        String dadosItem1 = dataItem[0].replaceAll("\\[", "");
        String dadosItem2 = dataItem[1];
        String dadosItem3 = dataItem[2].replaceAll("]", "");

        return Arrays.asList(dadosItem1, dadosItem2, dadosItem3);
    }
}
