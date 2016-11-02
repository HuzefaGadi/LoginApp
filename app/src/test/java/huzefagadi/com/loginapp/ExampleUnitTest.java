package huzefagadi.com.loginapp;

import org.junit.Test;

import huzefagadi.com.loginapp.utils.ValidationUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {


    @Test
    public void usernameValidatorCorrect() {
        assertThat(new ValidationUtils().isStringValid("hu45zefagadi"), equalTo(1));
    }

    @Test
    public void usernameValidatorIncorrectSymbol() {
        assertThat(new ValidationUtils().isStringValid("huze$fagadi"), equalTo(2));
    }
    @Test
    public void usernameValidatorIncorrect() {
        assertThat(new ValidationUtils().isStringValid(null), equalTo(3));
    }
}