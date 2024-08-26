package dkcorp.user_service.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotFoundExceptionTest {
    @Test
    void testExceptionMessage() {
        String errorMessage = "Entity not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionThrown() {
        String errorMessage = "Entity not found";

        assertThatThrownBy(() -> {
            throw new NotFoundException(errorMessage);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage(errorMessage);
    }
}
