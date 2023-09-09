package med.voll.api.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccion;

public record DatosActualizarPaciente(@NotNull Long id, String nombre, DatosDireccion direccion, String documento) {

}
