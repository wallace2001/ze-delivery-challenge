# Desafio Zé Delivery - API de Parceiros

Este projeto foi desenvolvido como parte do desafio proposto pelo Zé Delivery. Consiste em uma API REST que gerencia parceiros de entrega de bebidas, oferecendo funcionalidades como criação de parceiros, busca por ID e busca por localização.

## Funcionalidades

### 1. Criar um Parceiro

- Salva no banco de dados todas as informações de um parceiro, incluindo nome comercial, nome do proprietário, documento, área de cobertura e endereço.
- O campo "address" segue o formato GeoJSON Point.
- O campo "coverageArea" segue o formato GeoJSON MultiPolygon.
- O campo "document" deve ser único entre os parceiros.
- O campo "id" deve ser único entre os parceiros, mas não necessariamente um número inteiro.

### 2. Carregar Parceiro pelo ID

- Retorna um parceiro específico com base no seu ID, incluindo todas as informações associadas.

### 3. Buscar Parceiro por Localização

- Dada uma localização (coordenadas long e lat), busca o parceiro mais próximo cuja área de cobertura inclua essa localização.

## Instalação e Uso

Para executar o projeto localmente, siga as instruções abaixo:

1. Clone o repositório:

````
git clone <URL_DO_REPOSITORIO>
````

2. Navegue até o diretório do projeto:

````
cd nome-do-diretorio
````

3. Execute o Docker Compose para construir e iniciar os contêineres Docker:

````
docker-compose up -d --build
````

4. Acesse o Swagger para testar as rotas:

[http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)

## Contribuição

Contribuições são bem-vindas! Se você encontrar problemas ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto é licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).

## Contato

Para mais informações ou dúvidas, entre em contato com [seu-email@exemplo.com](mailto:dev_kollen@outlook.com).

---

© 2024 Wallace. Todos os direitos reservados.
