package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.hint.Hint;

/**
 * Represents a Card in the card folder.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {

    // Identity fields
    private final Question question;
    private final Answer answer;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Hint> hints = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Card(Question question, Answer answer, Email email, Address address, Set<Hint> hints) {
        requireAllNonNull(question, answer, email, address, hints);
        this.question = question;
        this.answer = answer;
        this.email = email;
        this.address = address;
        this.hints.addAll(hints);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable hint set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Hint> getHints() {
        return Collections.unmodifiableSet(hints);
    }

    /**
     * Returns true if both cards of the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two cards.
     */
    public boolean isSameCard(Card otherCard) {
        if (otherCard == this) {
            return true;
        }

        return otherCard != null
                && otherCard.getQuestion().equals(getQuestion())
                && (otherCard.getAnswer().equals(getAnswer()) || otherCard.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both cards have the same identity and data fields.
     * This defines a stronger notion of equality between two cards.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;
        return otherCard.getQuestion().equals(getQuestion())
                && otherCard.getAnswer().equals(getAnswer())
                && otherCard.getEmail().equals(getEmail())
                && otherCard.getAddress().equals(getAddress())
                && otherCard.getHints().equals(getHints());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, email, address, hints);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Hint: ");
        getHints().forEach(builder::append);
        return builder.toString();
    }

}
