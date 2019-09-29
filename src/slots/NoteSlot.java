package slots;

import payments.MoneyPaymentMethod;
import payments.money.Note;

import java.util.ArrayList;

public class NoteSlot  extends Slot{

    private ArrayList<Note> insertedNotes = new ArrayList<Note>();

    public NoteSlot(MoneyPaymentMethod[] acceptableList) {
        super(acceptableList);
        super.slotType = "Note";
    }

    public double insertNote( Note note){
        doesMachineAccept(note);
        insertedNotes.add(note);
        insertedMoney+= note.getValue();
        return note.getValue();
    }

    public ArrayList<Note> refund(){
        ArrayList temp = insertedNotes;
        insertedNotes = new ArrayList<Note>();
        insertedMoney = 0;
        return temp;
    }

}
