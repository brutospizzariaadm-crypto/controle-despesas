package br.uninga.controledespesas.service;

import br.uninga.controledespesas.model.Despesa;
import java.util.ArrayList;
import java.util.List;

public class DespesaService {
    private List<Despesa> despesas = new ArrayList<>();
    private int idCounter = 1;

    public void adicionarDespesa(String descricao, double valor, String data, String categoria) {
        despesas.add(new Despesa(idCounter++, descricao, valor, data, categoria));
        System.out.println("Despesa adicionada com sucesso!");
    }

    public void editarDespesa(int id, String novaDesc, double novoValor) {
        for (Despesa d : despesas) {
            if (d.getId() == id) {
                d.setDescricao(novaDesc);
                d.setValor(novoValor);
                System.out.println("Despesa atualizada!");
                return;
            }
        }
        System.out.println("Despesa não encontrada!");
    }

    public void excluirDespesa(int id) {
        despesas.removeIf(d -> d.getId() == id);
        System.out.println("Despesa excluída!");
    }

    public void listarDespesas() {
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
        } else {
            despesas.forEach(System.out::println);
        }
    }

    public double calcularTotal() {
        return despesas.stream().mapToDouble(Despesa::getValor).sum();
    }
}
