package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void isValidAnswer() {
        // null answer number
        Assert.assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid answer numbers
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only
        assertFalse(Answer.isValidAnswer("91")); // less than 3 numbers
        assertFalse(Answer.isValidAnswer("answer")); // non-numeric
        assertFalse(Answer.isValidAnswer("9011p041")); // alphabets within digits
        assertFalse(Answer.isValidAnswer("9312 1534")); // spaces within digits

        // valid answer numbers
        assertTrue(Answer.isValidAnswer("911")); // exactly 3 numbers
        assertTrue(Answer.isValidAnswer("93121534"));
        assertTrue(Answer.isValidAnswer("124293842033123")); // long answer numbers
    }
}