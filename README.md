# Análise e Avaliação Experimentais de Técnicas para Recuperação de Documentos Jurisprudenciais

Colocarei neste repositório os artefatos que elaboramos para execução dos experimentos relatados na minha [Dissertação de Mestrado](https://www.dropbox.com/s/gxx0loacc9rt168/Disserta%C3%A7%C3%A3o.pdf?dl=0).

Questões de pesquisa respondidas pelos experimentos:

* Q1: No contexto jurisprudencial, a aplicação de algoritmos de radicalização reduz de forma significativa a quantidade de termos únicos por documento?
* Q2: A eficácia dos algoritmos de radicalização é a mesma em todas as coleções judiciais?
* Q3: A radicalização tem efeito sobre os resultados obtidos mediante as buscas jurisprudenciais?

A experimentação foi divida em 3 fases:

* fase1: experimento de radicalização sobre uma amostra da coleção para saber a eficácia dos algoritmos;
* fase2: indexação de todos os documentos no engine de busca;
* fase3: cálculo das métricas em função de consultas disparadas contra a base indexada com todos os documentos;

Dentro dos diretórios `csv` e `trec` coloquei os arquivos que foram gerados durante a execução do experimento em minha máquina.

## Empacotamento dos experimentos

Para facilitar a reprodução destes experimentos, encapsulamos toda a estrutura necessária dentro de um container Docker. 

Ressaltamos que o build levará um certo tempo, pois é feito download da jurisprudência do [Tribunal de Justiça do Estado de Sergipe (coletada em setembro de 2016)](https://osf.io/as8uv/).

1. Faça um clone do projeto:

```
git clone https://github.com/ranophoenix/radicalizacaojurisprudencial.git
```

2. Entre no diretório `radicalizacaojurisprudencial` e execute um build:

```
docker build -t ranophoenix/radicalizacaojurisprudencial .
```

3. Ainda dentro do diretório, execute o container:

```
docker run -rm --name radicalizacaojurisprudencial -it ranophoenix/radicalizacaojurisprudencial
```

O container irá executar as 3 fases automaticamente. Ao final, você estará no shell do container e poderá ver os arquivos que foram gerados nos diretórios `/opt/experimentoradicalizacao/trec` e `/opt/experimentoradicalizacao/csv`.

Caso queira, você pode montar esses diretórios no seu host para analisá-los com sua ferramenta preferida. Para tal, basta montar os diretórios por meio de [volumes](https://docs.docker.com/engine/admin/volumes/volumes/).

## Publicações

As seguintes publicações foram frutos deste trabalho:

[Summary Report of Experimental Analysis of Stemming Algorithms Applied to Judicial Jurisprudence](https://link.springer.com/chapter/10.1007/978-3-319-54978-1_120)

[Assessing the Impact of Stemming Algorithms Applied to Judicial Jurisprudence - An Experimental Analysis](http://www.scitepress.org/Papers/2017/63171/index.html)

[Experimental Analysis of Stemming on Jurisprudential Documents Retrieval](http://www.mdpi.com/2078-2489/9/2/28)
