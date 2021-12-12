package com.course.springfood.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.course.springfood.client.api.ClientApiException;
import com.course.springfood.client.api.RestauranteClient;
import com.course.springfood.client.model.RestauranteModel;
import com.course.springfood.client.model.input.CidadeIdInput;
import com.course.springfood.client.model.input.CozinhaIdInput;
import com.course.springfood.client.model.input.EnderecoInput;
import com.course.springfood.client.model.input.RestauranteInput;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        try {
            var restTemplate = new RestTemplate();
            var restauranteClient = new RestauranteClient(
                    restTemplate, "http://localhost:8080");

            var cozinha = new CozinhaIdInput();
            cozinha.setId(1L);

            var cidade = new CidadeIdInput();
            cidade.setId(1L);

            var endereco = new EnderecoInput();
            endereco.setCidade(cidade);
            endereco.setCep("15800-000");
            endereco.setLogradouro("Rua Brasil");
            endereco.setNumero("100");
            endereco.setBairro("Centro");

            var restaurante = new RestauranteInput();
            restaurante.setNome("Comida Indiana");
            restaurante.setTaxaFrete(new BigDecimal(10.5));
            restaurante.setCozinha(new CozinhaIdInput());
            restaurante.setCozinha(cozinha);
            restaurante.setEndereco(endereco);

            RestauranteModel restauranteModel = restauranteClient.adicionar(restaurante);

            System.out.println(restauranteModel);
        } catch (ClientApiException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());

                e.getProblem().getObjects().stream()
                        .forEach(p -> System.out.println("- " + p.getUserMessage()));

            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
            }
        }
    }

}