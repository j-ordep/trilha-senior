/*
    Rust tudo é imutavel por padrão, ou seja, uma vez que um valor é atribuído a uma variável, ele não pode ser alterado.
    Para criar uma variável mutável, é necessário usar a palavra-chave `mut`.
    Isso promove a segurança e a previsibilidade do código, evitando efeitos colaterais inesperados.

- Ownership (Propriedade):
    Em Rust, cada valor tem um único dono (owner), que é responsável pela sua memória.
    Quando o owner sai de escopo, o valor é automaticamente desalocado (drop).
    Atribuições e passagens de função movem (move) a ownership por padrão,
    invalidando o uso da variável anterior, exceto para tipos Copy.
    Isso elimina a necessidade de garbage collector e evita double free/use-after-free.
   
- Borrowing (Empréstimo):
    permite acessar um valor sem transferir sua ownership.
    Referências (&T) são imutáveis e permitem múltiplos acessos simultâneos.
    Referências mutáveis (&mut T) permitem modificação, mas são exclusivas.
    Regras: ou várias referências imutáveis, ou uma mutável (nunca ambas ao mesmo tempo).
    O compilador (borrow checker) garante essas regras em tempo de compilação,
    prevenindo data races e garantindo segurança de memória.
*/

fn main() {
    // let my_string = ("Hello, world!");

    // se tentar printar my_string sem referenciar na memoria ou sem clonar, o compilador vai reclamar, 
    // pois a ownership foi movida para my_string2

    // ownership
    let mut my_string = String::from("Hello, world!");
    let my_string1 = String::from("Hello, world!");
    let my_string2 = &my_string;          // borrowing: my_string2 é uma referência a my_string, não é o dono
    let my_string3 = my_string2.clone();   // my_string3 tem sua própria alocação de memória
    let my_string4 = my_string1;           // ownership: my_string4 é o novo dono de my_string, my_string não pode mais ser usado

    // borrowing
    println!("{} 1", my_string);
    // println!("{} 1", my_string); // ownership: my_string foi movida para my_string4, não pode mais ser usada
    println!("{} 2", my_string2);
    println!("{} 3", my_string3); // por ser um clone, podemos usar as duas variáveis, pois cada uma tem sua própria alocação de memória
    println!("{} 4", my_string4);

    my_string = String::from("Hello, Rust!");
    println!("{} 1", my_string);
}
