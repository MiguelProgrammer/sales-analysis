# Application - Sales Analysis - Challenge

## Descrição

    Criar um sistema de análise de dados de venda que irá importar lotes de arquivos e produzir
    um relatório baseado em informações presentes nos arquivos.
 
    Existem 3 tipos de dados dentro dos arquivos e eles podem ser distinguidos pelo seu
    identificador que estará presente na primeira coluna de cada linha, onde o separador de
    colunas é o caractere “ç”.
 
    Dados do vendedor
 
    Os dados do vendedor possuem o identificador 001 e seguem o seguinte formato:
    001çSalesmanIDçNameçSalary
 
    Dados do cliente
 
    Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:
    002çClientIDçNameçBusiness 
    
    Area Dados de venda
    
    Os dados de venda possuem o identificador 003 e seguem o seguinte formato:
    003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

    Exemplo de conteúdo total do arquivo:
 

	001ç1234567891234çPedroç50000
	001ç3245678865434çPauloç40000.99
	002ç2345675434544345çJose da SilvaçRural
	002ç2345675433444345çEduardo PereiraçRural
	003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
	003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
    
    O sistema deverá ler continuamente todos os arquivos dentro do diretório padrão
    HOMEPATH/data/in e colocar o arquivo de saída em HOMEPATH/data/out.
    
    No arquivo de saída o sistema deverá possuir os seguintes dados:
    • Quantidade de clientes no arquivo de entrada
    • Quantidade de vendedores no arquivo de entrada
    • ID da venda mais cara
    • O pior vendedor, que vendeu o menor valor acumulado
    
    Requisitos
 
    • O sistema deve ser construído utilizando Java
    • O sistema deve rodar continuamente e capturar novos arquivos assim que eles sejam 
    inseridos no diretório padrão.
    • Os arquivos capturados deverão ser importados para um banco de dados postgreSQL
    • Você tem que utilizar as funcionalidades do Java Springboot para importar os 
    arquivos e gerar os relatórios
    • Você tem total liberdade para adicionar qualquer outra biblioteca externa se achar necessário.

## Solução

![alt text](https://i.imgur.com/ELCaceu.png)
    
  * A cada arquivo.txt inserido no path indicado, a aplicação irá executar os passos construidos para tratamento do arquivo
  * É verificado o formato do arquivo, se já existe algum item, venda, vendedor já inseridos, assim o registro em base desse dado é evitado
  * A cada arquivo lido, há um novo arquivo de saída respectivo
  * Os dados são persistidos em base.
    

## Solução técnica

  * Orientada a Objetos
  * Arquitetura em Camadas
  * PostgreSql
  * Clean Code / Solid
  * Dockerfile && compose
  * Docker Hub -> ``` docker push migprogrammer/sales:latest ```
  * Lombok

## Execução
  * Crie os diretorios abaixo
    * C:\processa arquivos\data\
      * Diretorio chamado in
        * dados.txt
      * Diretorio chamado out
  * Execute a aplicação
  * Comando para rodar a aplicação ``` java -jar nome-application.jar ```

## Soluções alternativas

  * Os itens de venda poderiam ser tratados com spring batch porque já muitas ferramentas já existentes que poderiam diminuir o gap
  * Os dados poderiam também ser tratados com mensageria
    * Arquivos incorretos seria publicados em filas de arquivos incorretos, posterior a isso, os responsáveis poderiam receber um relatório de arquivos incorretos
  * Poderia tratar outros tipos de arquivos, desta forma, haveria outros módulos voltados para o tipo de arquivo inserido
    * Este é um ponto importante, para cada typo de arquivo há uma tratativa diferente, além de deixar implicito que deverá haver um template a ser seguido
    
## Dados & Formatos arquivo de entrada
````
001ç1234000034çMariaç50000
001ç3000065434çSarahç4300
002ç2302309005çDéborajçRural
002ç3430400002çJéssicaçRural
003ç19ç[1-10-130,2-30-32.50,3-4-3.40]çMaria
003ç90ç[41-34-10,2-33-13.50,4-12-0.230]çSarah
````

## Dados & Formatos arquivo de saída - relatório
````
    --------------------------------- RELATÓRIO 24/11/2024 03:02:06 --------------------
* Arquivo processado = miguel.txt
* Quantidade de clientes no arquivo de entrada = 2
* Quantidade de vendedores no arquivo de entrada = 2
* ID da venda mais cara = 19 | Valor R$ 165.90
* O pior vendedor, que vendeu o menor valor acumulado = 90 | Valor R$ 23.73
--------------------------------- RELATÓRIO 24/11/2024 03:02:06 --------------------
````