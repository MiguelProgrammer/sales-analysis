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

        log.info("Leitura do arquivo de entrada iniciada ...");
        readerFileService.readDataIn(caminhoIn + filename);
        log.info("Leitura do arquivo de entrada finalizada...");

        Files.deleteIfExists(Path.of(caminhoOut + filename));

        log.info("Escrita do arquivo de saída iniciada ...");
        writeInFile.writeDataOut(caminhoOut + filename, caminhoIn + filename, clientList, salesmanList);
        log.info("Escrita do arquivo de saída finalizada ...");
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
                    List<Salesman> byName = salesmanRepository.getByName(name);

                    if(byName.isEmpty()) {
                        salesmanList.add(salesmanRepository.save(salesman));
                    } else {
                        log.info("Salesman já existente!");
                    }
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

                        Item save = null;
                        Optional<Item> itemByIdItem = itemRepository.findByIdItem(idItem);
                        if(!itemByIdItem.isPresent()) {
                            Item item = new Item(idItem, quantity, price);
                            save  = itemRepository.save(item);
                        } else {
                            log.info("Item já existente!");
                        }

                        itemList.add(save);

                    });

                    List<Salesman> salesmanList = salesmanRepository.getByName(dados[3]);

                    if (!salesmanList.isEmpty()) {

                       Salesman salesman = salesmanList.get(0);

                        String idSale = dados[1];
                        Sale sale = new Sale(idSale, itemList, salesman, valorTotalCompra.get());
                        List<Sale> byIdSale = saleRepository.findByIdSale(idSale);
                        if(byIdSale.isEmpty()) {
                            Sale save = saleRepository.save(sale);

                            /**
                             * ATUALIZA VENDA E VENDEDOR
                             */
                            saleList.add(save);
                            salesman.setSale(sale);
                            salesmanRepository.saveAndFlush(salesman);
                        } else {
                            log.info("Sale já existente!");
                        }
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

    public String getCaminhoIn() {
        return caminhoIn;
    }
}
