package cz.sliva.utils.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonValidatorTest {

    private final JsonValidator jsonValidator = new JsonValidator();

    @Test
    public void shouldBeValid() {
        assertTrue(jsonValidator.isStringValid("{\"foo\": \"bar\"}"));
    }

    @Test
    public void shouldNotBeValid() {
        assertFalse(jsonValidator.isStringValid("\"foo\": \"bar\""));
    }
}