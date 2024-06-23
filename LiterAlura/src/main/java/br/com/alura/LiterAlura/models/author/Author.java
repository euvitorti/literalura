
package br.com.alura.LiterAlura.models.author;

import br.com.alura.LiterAlura.models.book.Book;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int birthYear;
    private int deathYear;

    //RELACIONANDO COM A TABELA AUTHOR
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> bookList = new ArrayList<>();

    public Author(){}

    public Author(String name, int birthYear, int deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public void addBook(Book book) {
        book.setAuthor(this);
        bookList.add(book);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nAuthor: " + name + "\n" +
                "Birth Year: " + birthYear + "\n" +
                "Death Year: " + deathYear;
    }
}
