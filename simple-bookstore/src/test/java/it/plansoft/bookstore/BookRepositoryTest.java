package it.plansoft.bookstore;

import it.plansoft.bookstore.book.Book;
import it.plansoft.bookstore.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        Book newBook = new Book();
        newBook.setTitle("Il nome della rosa");
        newBook.setAuthor("Umberto Eco");
        newBook.setUpdated(new Date());
        bookRepository.saveAndFlush(newBook);
    }
    @Test
    public void testSaveBookKo() {
        Book newBook = new Book();
        newBook.setAuthor("Umberto Eco");
        newBook.setUpdated(new Date());
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.saveAndFlush(newBook);
        } );
    }

    @Test
    public void testFindByAuthor() {
        Book newBook = new Book();
        newBook.setTitle("Il nome della rosa");
        newBook.setAuthor("Umberto Eco");
        newBook.setUpdated(new Date());
        bookRepository.saveAndFlush(newBook);
        List<Book> found = bookRepository.findByAuthor("Umberto Eco");
        assertTrue(found != null && found.size() > 0);
    }

    @Test
    public void testFindByAuthorKo() {
        Book newBook = new Book();
        newBook.setTitle("Il nome della rosa");
        newBook.setAuthor("Umberto Eco");
        newBook.setUpdated(new Date());
        bookRepository.saveAndFlush(newBook);
        List<Book> found = bookRepository.findByAuthor("Mario Rossi");
        assertTrue(found != null && found.size() == 0);
    }

    @Test
    public void testFindById() {
        Book newBook = new Book();
        newBook.setTitle("Il nome della rosa");
        newBook.setAuthor("Umberto Eco");
        newBook.setUpdated(new Date());
        bookRepository.saveAndFlush(newBook);
        Optional<Book> found = bookRepository.findById(newBook.getId());
        assertTrue(found.isPresent());

    }

    @Test
    public void testFindByIdKo() {
        Optional<Book> found = bookRepository.findById(1);
        assertFalse(found.isPresent());
    }

}
