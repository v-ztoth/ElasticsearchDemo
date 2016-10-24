package itv.com.http;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {

        Optional<Exception> optionalException = Optional.ofNullable(exception);

        Response response500 = Response
                .serverError()
                .entity(optionalException.map(Exception::getMessage).orElse("") + "Cause: "
                        + optionalException.map(Exception::getCause).map(Throwable::getMessage).orElse(""))
                .build();

        return response500;
    }
}