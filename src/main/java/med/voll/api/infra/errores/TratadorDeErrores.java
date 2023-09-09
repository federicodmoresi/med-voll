package med.voll.api.infra.errores;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class TratadorDeErrores {
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarError404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
		var errores=e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity tratarErrorDatoDuplicado(SQLIntegrityConstraintViolationException e) {
		
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}	

	@ExceptionHandler(ValidacionDeIntegridad.class)
	public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e) {
		
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity errorHandlerValidaciones(Exception e) {
		
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	
	
	
	private record DatosErrorValidacion(String campo, String error) {
		
		public DatosErrorValidacion(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
		
	}
	
	
	
}
