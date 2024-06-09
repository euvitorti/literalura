
package br.com.alura.LiterAlura.models.book;

import br.com.alura.LiterAlura.dto.AuthorDTO;
import br.com.alura.LiterAlura.dto.ResultDataBookDTO;
import br.com.alura.LiterAlura.models.author.Author;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

//    private List<AuthorDTO> authors;

    @ElementCollection
    private List<String> subjects;

    @ElementCollection
    private List<String> languages;

    @ElementCollection
    @MapKeyColumn(name = "format_type")
    @Column(name = "format_url")
    private Map<String, String> formats;

    // RELACIONANDO COM A TABELA SERIES

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(){}

    public Book(ResultDataBookDTO data) {

        this.title = data.title();
//        this.authors = data.authors();
        this.subjects = data.subjects();
        this.languages = data.languages();
        this.formats = data.formats();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    @Override
    public String toString() {

//        Para os autores, usamos stream() e collect(Collectors.joining(", ")) para concatenar os nomes com uma vírgula.
//        Para os sujeitos e idiomas, usamos String.join(", ", list)
//        para criar uma string separada por vírgulas.
//        Para os formatos, usamos entrySet().stream(),
//        transformando cada entrada do mapa em uma string e juntando-as com quebras de linha.
//        Finalmente, montamos uma string final no método toString que organiza os dados de forma legível.

//        String authorsStr = authors.stream()
//                .map(AuthorDTO::toString)
//                .collect(Collectors.joining(", "));

        String subjectsStr = String.join(", ", subjects);

        String languagesStr = String.join(", ", languages);

        String formatsStr = formats.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return "----------------------------" + "\n" +
                "Title: " + title + "\n" +
//                "Author: " + authors + "\n" +
                "Language: " + languages + "\n" +
                "Read online: " + formats + "\n" +
                "----------------------------";
    }
}
