package it.plansoft.bookstore;

import it.plansoft.bookstore.book.Book;
import it.plansoft.bookstore.book.BookController;
import it.plansoft.bookstore.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

        @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookController bookController;

    @Test
    public void testFindById() {
        Optional<Book> book1 = newBook(1);
        when(bookRepository.findById(1)).thenReturn(book1);
        Book result = bookController.getByID(1);
        assertEquals("Il nome della rosa", result.getTitle());
    }

    /**
     * In questo caso il mock viene utilizzato per verificare che al
     * momento in cui viene invocato il metodo save() del repository
     * il parametro passato sia valorizzato in modo corretto.
     */
    @Test
    public void testSave() {
        Optional<Book> book1 = newBook(1);
        bookController.save(book1.get());
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().getUpdated() != null);
    }

    private static Optional<Book> newBook(Integer id) {
        Optional<Book> result;
        switch(id) {
            case 1:
                result = Optional.of(Book.builder().id(1)
                        .author("Umberto Eco")
                        .title("Il nome della rosa")
                        .updated(new Date())
                        .build());
                break;
            case 2:
                result = Optional.of(Book.builder().id(2)
                        .author("Antonio Scurati")
                        .title("M il figlio del secolo")
                        .updated(new Date())
                        .build());
                break;
            default:
                result = Optional.empty();
                break;
        }

        return result;
    }
}
