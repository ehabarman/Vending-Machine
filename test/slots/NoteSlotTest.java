package slots;

import Exceptions.SlotSupportLimitationException;
import enums.Currency;
import org.junit.Test;
import payments.money.Note;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NoteSlotTest {
    @Test
    public void NoteSlotTest() {
        Note[] acceptableNotes = {new Note(Currency.USD, 20), new Note(Currency.USD, 50)};
        NoteSlot noteSlot = new NoteSlot(acceptableNotes);

        assertThrows(SlotSupportLimitationException.class, () -> {
            noteSlot.insertNote(new Note(Currency.USD, 0.6));
        });

        Note note = new Note(Currency.USD, 20);
        assertEquals(true, noteSlot.insertNote(note) == 20);
        note = new Note(Currency.USD, 50);
        assertEquals(50, noteSlot.insertNote(note), 0);
        assertEquals(70, noteSlot.getInsertedMoney(), 0);
        assertEquals(true, noteSlot.refund() instanceof ArrayList);
        assertEquals(0.0, noteSlot.getInsertedMoney(), 0);
        
    }
    
}