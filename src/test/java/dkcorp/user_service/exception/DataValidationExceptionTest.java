package dkcorp.user_service.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataValidationExceptionTest {
    @Test
    void testExceptionMessage() {
        String errorMessage = "User cannot follow himself";
        DataValidationException exception = new DataValidationException(errorMessage);

        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionThrown() {
        String errorMessage = "User cannot follow himself";

        assertThatThrownBy(() -> {
            throw new DataValidationException(errorMessage);
        }).isInstanceOf(DataValidationException.class)
                .hasMessage(errorMessage);
    }
}
