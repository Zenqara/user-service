package dkcorp.user_service.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityNotFoundExceptionTest {
    @Test
    void testExceptionMessage() {
        String errorMessage = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(errorMessage);

        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionThrown() {
        String errorMessage = "Entity not found";

        assertThatThrownBy(() -> {
            throw new EntityNotFoundException(errorMessage);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(errorMessage);
    }
}
