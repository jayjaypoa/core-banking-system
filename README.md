# CORE-BANKING-SYSTEM
Projeto consiste em basicamente em simular aspectos de uma conta bancária.<br/>
Dentre os endpoints expostos, destaca-se 2 (dois) principais: <br/>
* <b>SALDO</b>: Permite que seja verificado o saldo bancário de um determinado usuário.<br/>
* <b>TRANSAÇÃO</b>: Possibilita que seja efetuada operações de crédito (depósito de valores) ou de débito (retirada de valores) em uma conta corrente específica.<br/><br/>
Embora não fosse fundamental, alguns outros endpoints também foram desenvolvidos no intuito de auxiliar nos testes e cenários:<br/>
* Listar todas as contas.<br/>
* Listar uma conta específica pelo seu identificador.<br/>
* Inserir uma nova conta para uma determinada agência.<br/>

## Spring WebFlux
Este projeto foi desenvolvido em Java (versão 11), utilizando Spring 5 e framework funcional/reativo WebFlux.<br/>
Optou-se por esta abordagem devido a questões como concorrência e conflitos transacionais.

## Banco de Dados<br/>

Neste projeto foi utilizado o banco de dados não-relacional MongoDB.<br/>
Ao subir a aplicação as estrutura de Collections/Documents necessárias são automaticamente criadas.

## Portas Utilizadas
Esta aplicação utiliza as portas 8082 (core-banking-system) e 27017 (mongoDB).<br/>
Caso a aplicação seja rodada localmente, certifique-se que estas portas estejam liberadas ou, caso preferir, altere-as no arquivo <i>application.properties</i> do projeto.

## Dummy Data / Dados para testes
Ao inicializar a aplicação, são inseridas 15 (quinze) contas de usuários para fins de simulações e testes.<br/>
A inserção dos dados pode ser vista na seguinte classe: <i>br.com.bank.core.data.AccountDummyData</i><br/>
A collection no qual estes dados ficam armazenados é: <i>Account</i>

## Docker / Inicialização
A aplicação funciona em qualquer sistema operacional (incluindo Linux e MacOS).<br/>
Foi utilizado Docker, justamente para garantir tal possibilidade, pois desta forma automaticamente a aplicação indica seus requisitos e os obtêm.<br/>
Os arquivos <i>Dockerfile</i> e <i>docker-compose.yml</i>, ambos localizados na raiz do projeto, detalham melhor isto.<br/><br/>
Por fim, abaixo seguem alguns comandos para inicialização da aplicação e montagem de imagens do docker.<br/>

* Iniciando aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker-compose up```
* Parando aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker-compose stop```
* Criando uma imagem da aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker build -t core-banking-system:latest .```
