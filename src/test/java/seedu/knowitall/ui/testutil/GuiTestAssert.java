package seedu.knowitall.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.CardListPanelHandle;
import guitests.guihandles.CardThumbnailHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.knowitall.model.card.Card;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CardThumbnailHandle expectedCard, CardThumbnailHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCard}.
     */
    public static void assertCardDisplaysCard(Card expectedCard, CardThumbnailHandle actualCard) {
        assertEquals(expectedCard.getQuestion().fullQuestion, actualCard.getQuestion());
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cards} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CardListPanelHandle cardListPanelHandle, Card... cards) {
        for (int i = 0; i < cards.length; i++) {
            cardListPanelHandle.navigateToCard(i);
            assertCardDisplaysCard(cards[i], cardListPanelHandle.getCardCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cards} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CardListPanelHandle cardListPanelHandle, List<Card> cards) {
        assertListMatching(cardListPanelHandle, cards.toArray(new Card[0]));
    }

    /**
     * Asserts the size of the list in {@code cardListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CardListPanelHandle cardListPanelHandle, int size) {
        int numberOfPeople = cardListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
