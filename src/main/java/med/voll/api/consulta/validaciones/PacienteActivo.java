package med.voll.api.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.consulta.DatosAgendarConsulta;
import med.voll.api.paciente.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas {

	@Autowired
	private PacienteRepository repository;

	public void validar(DatosAgendarConsulta datos) {

		if (datos.idPaciente() == null) {
			return;
		}

		var pacienteActivo = repository.findActivoById(datos.idPaciente());

		if (!pacienteActivo) {
			throw new ValidationException("No se puede permitir agendar cita con pacientes inactivos en sistema");
		}
	}
}
