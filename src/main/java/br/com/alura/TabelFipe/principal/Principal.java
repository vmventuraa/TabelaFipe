package br.com.alura.TabelFipe.principal;

import br.com.alura.TabelFipe.model.Dados;
import br.com.alura.TabelFipe.model.Modelos;
import br.com.alura.TabelFipe.model.Veiculo;
import br.com.alura.TabelFipe.service.ConsumoApi;
import br.com.alura.TabelFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitor = new Scanner(System.in);

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados convsesor = new ConverteDados();


    public void exibeMenu() {


        var menu = """
                    *** OPÇÕES ***
                    Carro
                    Moto
                    Caminhão
                    
                    Digite uma das opções para consultar
                """;


        System.out.println(menu);
        var opcao = leitor.nextLine();

        String endereco;


        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";

        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }


        var json = consumoApi.obterDados(endereco);

        System.out.println(json);

        var marcas = convsesor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);


        System.out.println("Informe um codigo da marca a ser pesquisada");

        var codigoMarca = leitor.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";

        json = consumoApi.obterDados(endereco);
        var modeloLista = convsesor.obterDados(json, Modelos.class);

        System.out.println("Modelos desta marca: ");

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);


        System.out.println("\nInforme um trecho do modelo do carro");

        var nomeModelo = leitor.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeModelo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);


        System.out.println("Digite o código do modelo desejado para avaliação:");
        var codModelo = leitor.nextLine();

        endereco = endereco + "/" + codModelo + "/anos";

        json = consumoApi.obterDados(endereco);

        List<Dados> anos = convsesor.obterLista(json, Dados.class);


        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = convsesor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);


        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano");
        veiculos.forEach(System.out::println);


    }
}
