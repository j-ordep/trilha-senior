// Exemplo Ruim

class Message { // transormar em record
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

    // nome muito generico
    List<Message> data = Arrays.asList( // vamos usar List.of()
            new Message("Victor", "texto 1", false),
            new Message("Alisson", "texto 2", false),
            new Message("Victor", "texto 3", false)
    );

    String result = null; // quem mandou mais mensagem
    int bigger = 0; // quantas mensagens a pessoa mais enviou

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

    List<String> temp = new ArrayList<>(); // transformar em HASH SET
    for (Message m : data) {
        if (m.text != null && !m.text.isBlank() && !m.isRead) {
            if (!temp.contains(m.person)) { // se fosse um HASH SET não precisaria verificar com contains
                temp.add(m.person);
            }
        }
    }

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