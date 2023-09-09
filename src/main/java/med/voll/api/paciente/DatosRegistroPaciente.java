package med.voll.api.paciente;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.direccion.Direccion;

public record DatosRegistroPaciente(
		@NotBlank 
		String nombre, 
		@NotBlank @Pattern(regexp = "\\d{4,6}")
		String documento,
		@NotBlank 
		String email, 
		@NotBlank 
		String telefono, 
		@NotNull 
		DatosDireccion direccion) {

}
