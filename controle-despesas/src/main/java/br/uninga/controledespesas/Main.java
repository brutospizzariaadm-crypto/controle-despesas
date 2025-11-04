package br.uninga.controledespesas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== SISTEMA DE CONTROLE DE DESPESAS ===");
            System.out.println("1 - Entrar Despesa");
            System.out.println("2 - Anotar Pagamento");
            System.out.println("3 - Listar Despesas em Aberto no período");
            System.out.println("4 - Listar Despesas Pagas no período");
            System.out.println("5 - Gerenciar Tipos de Despesa");
            System.out.println("6 - Gerenciar Usuários");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    System.out.println("[ENTRAR DESPESA] - Função chamada (a implementar)");
                    break;
                case "2":
                    System.out.println("[ANOTAR PAGAMENTO] - Função chamada (a implementar)");
                    break;
                case "3":
                    System.out.println("[LISTAR DESPESAS EM ABERTO] - Função chamada (a implementar)");
                    break;
                case "4":
                    System.out.println("[LISTAR DESPESAS PAGAS] - Função chamada (a implementar)");
                    break;
                case "5":
                    System.out.println("[GERENCIAR TIPOS DE DESPESA] - Função chamada (a implementar)");
                    break;
                case "6":
                    System.out.println("[GERENCIAR USUÁRIOS] - Função chamada (a implementar)");
                    break;
                case "7":
                    System.out.println("Saindo...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println(); // linha em branco para separar iterações
        }

        sc.close();
    }
}
