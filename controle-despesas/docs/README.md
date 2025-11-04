# Controle de Despesas — Projeto Bimestral (PARTE 01)

**Aluno:** Anna Carolina Guilhen

## Objetivo Geral
Desenvolver um sistema de controle de despesas que permita gerenciar despesas e pagamentos, com armazenamento em arquivos de texto, classes orientadas a objetos, herança, interfaces, criptografia de senhas e documentação técnica.

## Entrega: B4T01.1 (Primeiro Commit -> 0.0.1)
- Repositório criado, clonado.
- Menu principal implementado.
- Ao selecionar cada opção do menu, o sistema imprime mensagens (println) confirmando a chamada da funcionalidade.

## Estrutura de Pastas
- `/src` — código fonte Java
- `/data` — arquivos de armazenamento (despesas, tipos, usuários)
- `/docs` — documentação e changelog

## Estratégia de construção do sistema
1. Criar modelos (classes) para `Despesa` (abstrata) e subclasses (Transporte, Eventual, Superfluo).
2. Implementar interface `Pagavel` para comportamento de pagamento.
3. Implementar repositórios que gravem/leiam arquivos de texto (`.txt`) separados para despesas, tipos e usuários.
4. Implementar criptografia de senhas (SHA-256) ao cadastrar usuários.
5. Documentar cada classe e método no README ou arquivos separados.

## Critério de avaliação (como solicitado pelo docente)
- 30%: sistema parcial
- 50%: sistema parcialmente feito sem/ pouca documentação
- 80%: feito com documentação completa
- 100%: arquivo correto e todas configurações ok

## CHANGELOG
### B4T01.1 — 0.0.1
- Repositório criado
- Menu principal implementado com `println` por opção (Entrar Despesa, Anotar Pagamento, Listar Despesas em Aberto, Listar Despesas Pagas, Gerenciar Tipos, Gerenciar Usuários, Sair)
