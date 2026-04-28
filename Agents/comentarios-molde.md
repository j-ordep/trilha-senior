# Molde para Anotação Didática de Código

## Objetivo

Analise arquivos de uma codebase de exercícios, estudos ou aprendizado e adicione comentários técnicos e didáticos diretamente no código.

O comentário deve ajudar a revisitar o arquivo no futuro e responder, com clareza:

- qual problema o código resolve;
- qual conceito está sendo praticado;
- por que a solução foi escrita desse jeito;
- quais decisões de design aparecem no trecho;
- quais limitações, riscos ou melhorias podem ser observados.

O foco não é transformar o arquivo em artigo. O foco é registrar raciocínio técnico com boa densidade de informação e baixa poluição visual.

---

## Papel do agente

Ao comentar código:

- explique intenção, conceito e trade-off;
- preserve a terminologia correta da linguagem usada no arquivo;
- diferencie fato observável de inferência;
- comente para ensino e revisão futura, não para narrar cada linha.

---

## Regras gerais

1. Preserve o código original sempre que possível.
2. Não altere lógica, nomes, fluxo ou estrutura sem pedido explícito.
3. Não invente intenção que não esteja evidente no código.
4. Se algo for inferência, sinalize com clareza.
5. Explique mais o "por quê" e o "qual aprendizado" do que o "o quê".
6. Evite comentários óbvios, como `// cria variável`, `// chama método` ou `// retorna resultado`.
7. Priorize blocos importantes: regras de negócio, validações, modelagem, fluxo principal, tratamento de erro, uso de abstrações e decisões de design.
8. Se houver problema de design, acoplamento excessivo, duplicação ou risco técnico, aponte com cuidado e sem dramatizar.
9. Não corrija automaticamente más práticas, a menos que isso tenha sido pedido.
10. Se o trecho for simples demais, não force comentário.
11. Use linguagem técnica, mas didática e direta.
12. Não confunda conceitos entre linguagens. O nome correto da abstração importa.

---

## Ordem de leitura antes de comentar

Antes de escrever comentários, siga esta sequência:

1. Identifique o objetivo do arquivo, classe, função ou módulo.
2. Descubra qual conceito principal está sendo exercitado.
3. Observe quais decisões estruturais aparecem: composição, herança, interface, trait, generics, tratamento de erro, imutabilidade, concorrência, etc.
4. Verifique se existe alguma limitação relevante, simplificação didática ou risco técnico.
5. Só então escolha onde comentar. Nem todo bloco precisa de anotação.

---

## O que vale comentar

- trechos que concentram regra de negócio;
- decisões arquiteturais ou de modelagem;
- uso de abstrações importantes;
- escolhas de mutabilidade, nulidade, posse, concorrência ou fluxo de erro;
- diferenças entre uma solução "de exercício" e uma solução "de produção";
- trade-offs de legibilidade, extensibilidade e segurança.

## O que não vale comentar

- linha autoexplicativa;
- detalhe que o próprio nome do método já explica;
- repetição do código em linguagem natural;
- opinião solta sem vínculo com o trecho;
- explicação genérica que serviria para qualquer linguagem.

---

## Formato padrão de comentário

Antes de blocos relevantes, prefira um comentário curto de visão geral. Adapte a sintaxe ao arquivo, mas preserve a estrutura:

```text
Contexto:
- Este trecho resolve ...

Conceito principal:
- Demonstra ...

Decisão de design:
- A solução usa ... porque ...

Aprendizado:
- O ponto mais importante aqui é ...

Ponto de atenção:
- Em produção, seria importante observar ...
```

### Regras de uso do formato

- Use todos os campos apenas quando fizer sentido.
- Se o bloco for pequeno, reduza para 2 ou 3 linhas.
- Não escreva comentário longo para trecho simples.
- Se o comentário for sobre API pública, use o estilo documental da linguagem quando apropriado, como `Javadoc`, `KDoc` ou `Rustdoc`.
- Se for comentário interno, prefira comentário comum e direto.

---

## Checklist de qualidade

Antes de finalizar, revise se o comentário:

- explica algo que não está óbvio no código;
- usa o termo correto da linguagem;
- separa fato de inferência;
- evita exagero didático;
- não contradiz o comportamento real do trecho;
- ajuda alguém a retomar o raciocínio no futuro.

---

## Regra crítica: não confundir linguagens

O agente deve respeitar a linguagem do arquivo e evitar equivalências imprecisas.

- Não trate composição como herança.
- Não chame `trait` de "classe abstrata" por aproximação.
- Não trate interface implícita de `Go` como se fosse igual a interface nominal de `Java`.
- Não trate tipos de `TypeScript` como garantias de runtime.
- Não trate extension function de `Kotlin` como alteração real da classe original.
- Não trate ownership de `Rust` como simples "imutabilidade".
- Não trate `Stream` de `Java` e coleções de `Kotlin` como se fossem a mesma abstração.

Se o conceito existir em mais de uma linguagem, explique pela semântica da linguagem atual, não por analogia preguiçosa.

---

## Peculiaridades por linguagem

### Java

Ao comentar `Java`, observe:

- herança, interfaces e polimorfismo nominal;
- diferença entre orientação a objetos clássica e uso funcional com `Stream`;
- presença possível de `checked exceptions`;
- mutabilidade normalmente mais explícita e mais comum;
- verbosidade estrutural como parte do custo da solução.

Evite:

- descrever `Stream` como se fosse apenas "um loop mais bonito";
- falar de interface como se fosse herança concreta;
- ignorar custo de legibilidade em cadeias longas de `Stream`.

Termos que costumam fazer sentido:

- classe concreta;
- interface;
- herança;
- sobrescrita;
- encapsulamento;
- fluxo com `Stream`;
- exceção checada ou não checada.

### Kotlin

Ao comentar `Kotlin`, observe:

- `null safety` como parte central da modelagem;
- diferença entre `val` e `var`;
- uso de `data class`, `sealed class`, `object` e extension functions;
- redução de verbosidade em relação a `Java`;
- preferência frequente por composição e modelagem mais expressiva.

Evite:

- falar de `val` como se tornasse o objeto inteiro imutável;
- tratar extension function como se alterasse a classe original;
- ignorar o papel de `sealed class` em restringir estados possíveis.

Termos que costumam fazer sentido:

- nulidade controlada;
- imutabilidade de referência;
- modelagem de estados;
- função de extensão;
- smart cast;
- data class.

### Go

Ao comentar `Go`, observe:

- simplicidade estrutural e preferência por composição;
- interfaces implícitas;
- tratamento de erro por retorno explícito;
- uso de `goroutines` e `channels` quando houver concorrência;
- baixo uso de hierarquia orientada a objetos tradicional.

Evite:

- procurar herança clássica onde ela não existe;
- tratar retorno de erro como exceção disfarçada;
- descrever interface como contrato nominal igual ao de `Java`.

Termos que costumam fazer sentido:

- composição;
- interface implícita;
- retorno de erro;
- função pequena e coesa;
- concorrência com goroutine;
- comunicação por channel.

### Rust

Ao comentar `Rust`, observe:

- ownership, borrowing e lifetimes como base do design;
- uso de `Result` e `Option` para segurança explícita;
- `match` e modelagem por enums;
- `trait` como mecanismo central de abstração;
- custo zero de abstrações como valor da linguagem.

Evite:

- reduzir ownership a "imutabilidade";
- explicar `borrow` sem mencionar regras de posse ou referência;
- tratar `trait` como equivalente exato de interface orientada a objetos clássica.

Termos que costumam fazer sentido:

- ownership;
- empréstimo imutável ou mutável;
- tempo de vida;
- segurança em tempo de compilação;
- enum algébrica;
- trait;
- propagação de erro com `Result`.

### TypeScript

Ao comentar `TypeScript`, observe:

- sistema de tipos estrutural;
- diferença entre checagem de tipos em compilação e comportamento real em runtime;
- uso de `union`, `intersection`, `generics` e `narrowing`;
- necessidade de validação em runtime quando o dado vem de fora;
- mistura frequente entre orientação a objetos, composição e estilo funcional.

Evite:

- assumir que tipo garante dado válido em execução;
- tratar interface como estrutura obrigatória de runtime;
- comentar como se `any` e `unknown` tivessem o mesmo papel.

Termos que costumam fazer sentido:

- tipo estrutural;
- narrowing;
- inferência de tipo;
- contrato em compilação;
- validação em runtime;
- generic.

---

## Exemplos curtos de bons comentários

### Exemplo 1

```java
/*
Contexto:
- Este fluxo usa Stream para transformar e filtrar dados de entrada.

Aprendizado:
- A legibilidade depende de a pipeline continuar curta; se acumular regra de negócio, pode valer extrair etapas nomeadas.
*/
```

### Exemplo 2

```kotlin
/*
Conceito principal:
- O uso de sealed class restringe os estados possíveis da operação e deixa o tratamento mais seguro no when.

Ponto de atenção:
- Isso melhora a modelagem, mas exige atualizar os ramos quando novos estados forem introduzidos.
*/
```

### Exemplo 3

```go
/*
Decisão de design:
- O erro é retornado explicitamente em vez de disparar exceção, seguindo o fluxo idiomático de Go.

Aprendizado:
- Esse estilo torna o caminho de falha visível no chamador e reduz comportamento implícito.
*/
```

### Exemplo 4

```rust
/*
Conceito principal:
- O borrow evita cópia desnecessária e mantém a posse com o chamador.

Ponto de atenção:
- Esse ganho vem com regras mais rígidas de referência, que precisam ser respeitadas para manter segurança em compilação.
*/
```

### Exemplo 5

```ts
/*
Conceito principal:
- O union type representa mais de um formato válido de entrada.

Ponto de atenção:
- O narrowing organiza a lógica em compilação, mas dados externos ainda podem exigir validação em runtime.
*/
```

---

## Regra final

Comente para registrar raciocínio técnico, não para preencher espaço.

Se o comentário não ajuda alguém a entender intenção, conceito, trade-off ou risco, ele provavelmente não precisa existir.
