package payments.money;

import enums.Currency;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteTest {

    @Test
    public void NoteTest(){
        Note note = new Note(Currency.USD, 20);
        assertEquals(20.0,note.getValue(),0);
        assertEquals(Currency.USD,note.getCurrency());
        assertEquals(0,note.compareTo(new Note(Currency.USD, 20)));
        assertNotEquals(0,note.compareTo(new Note(Currency.EURO, 20)));
    }

}