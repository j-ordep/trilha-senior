// Exemplo propositalmente verboso para contrastar com versões mais idiomáticas do mesmo exercício.

class Message { // Em Java moderno, este agregado simples é um candidato natural a record.
    public String person;
    public String text;
    public Boolean isRead;

    public Message(String person, String text, boolean isRead) {
        this.person = person;
        this.text = text;
        this.isRead = isRead;
    }
}

void main() {

    /*
    Contexto:
    - A coleção abaixo funciona como massa de dados do exercício.

    Ponto de atenção:
    - Arrays.asList cria uma lista de tamanho fixo; em uma revisão posterior, List.of comunicaria melhor a intenção de dados literais.
    */
    List<Message> data = Arrays.asList(
            new Message("Victor", "texto 1", false),
            new Message("Alisson", "texto 2", false),
            new Message("Victor", "texto 3", false)
    );

    String result = null; // Guarda o remetente com maior frequência encontrada.
    int bigger = 0; // Guarda a maior contagem observada até o momento.

    /*
    Conceito principal:
    - A contagem por remetente é resolvida com dois loops aninhados.

    Aprendizado:
    - A solução é fácil de seguir, mas tem custo O(N²) porque cada elemento é comparado com todos os demais.
    */
    for (Message m1 : data) { // O(N)
         int counter = 0;
         for (Message m2 : data) { // O(N)
            if (m1.person.equals(m2.person)) {
                counter++;
            }
         }
         if (counter > bigger) {
             bigger = counter;
             result = m1.person;
         }
    }

    System.out.println(result);

    /*
    Contexto:
    - Este bloco filtra mensagens não lidas com texto válido e tenta manter apenas remetentes únicos.

    Decisão de design:
    - Como a estrutura escolhida é um ArrayList, a deduplicação depende de contains e de um if extra.
    */
    List<String> temp = new ArrayList<>(); // Um Set reduziria a preocupação manual com duplicatas.
    for (Message m : data) {
        if (m.text != null && !m.text.isBlank() && !m.isRead) {
            if (!temp.contains(m.person)) { // Com um Set, a própria inserção já absorveria a regra de unicidade.
                temp.add(m.person);
            }
        }
    }

    /*
    Conceito principal:
    - A ordenação é implementada manualmente por trocas.

    Ponto de atenção:
    - A biblioteca padrão de Java já oferece ordenação pronta; aqui o valor didático está em expor o custo de manter esse tipo de lógica na mão.
    */
    for (int i = 0; i < temp.size(); i++) {
        for (int j = 0; j < temp.size(); j++) {
            if (temp.get(i).compareTo(temp.get(j)) > 0) {
                String swap = temp.get(i);
                temp.set(i, temp.get(j));
                temp.set(j, swap);
            };
        }
    }

    System.out.println(temp);

}
