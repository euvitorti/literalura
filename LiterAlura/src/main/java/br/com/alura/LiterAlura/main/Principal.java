
package br.com.alura.LiterAlura.main;

import br.com.alura.LiterAlura.dto.DataBookDTO;
import br.com.alura.LiterAlura.models.author.Author;
import br.com.alura.LiterAlura.models.book.Book;
import br.com.alura.LiterAlura.repository.author.AuthorRepository;
import br.com.alura.LiterAlura.repository.book.BookRepository;
import br.com.alura.LiterAlura.service.ConnectApi;
import br.com.alura.LiterAlura.service.ConvertData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConnectApi connectApi = new ConnectApi();

    private ConvertData convertData = new ConvertData();

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    public Principal(){}

    public Principal(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void menu() {
        byte choice = -1;

        while (choice != 0) {
            System.out.println("""
                    Welcome! 🎥
                    ------------------------------------
                    [1] Buscar Livro
                    ------------------------------------
                    [0] Sair
                    """);

            choice = scanner.nextByte();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBook();
                    break;
                case 0:
                    System.out.println("Goodbye👋");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private DataBookDTO getDataBook() {

        var json = connectApi.connect();

        DataBookDTO dataBookDTO = convertData.getData(json, DataBookDTO.class);
        return dataBookDTO;
    }

    private void searchBook() {
        DataBookDTO data = getDataBook();

//        List<Book> books = data.results().stream()
//                .map(Book::new)
//                .collect(Collectors.toList());
//
//        books.forEach(System.out::println);

        // SALVANDO OS DADOS NO BANCO DE DADOS
        List<Book> books = data.results().stream()
                .map(result -> {
                    Book book = new Book(result);
                    result.authors().forEach(authorDTO -> {
                        Author author = new Author(authorDTO.name(), authorDTO.birth_year(), authorDTO.death_year());
                        author.addBook(book);
                        authorRepository.save(author);
                    });
                    return book;
                })
                .collect(Collectors.toList());

        books.forEach(bookRepository::save);

    }
}

