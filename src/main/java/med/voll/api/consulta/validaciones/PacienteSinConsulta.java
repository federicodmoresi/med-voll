package med.voll.api.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DatosAgendarConsulta;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DatosAgendarConsulta datos) {
		
		var primerHorario=datos.fecha().withHour(7);
		var ultimoHorario=datos.fecha().withHour(18);
		
		var pacienteConConsulta=repository.existsByPacienteIdAndDataBetween(datos.idPaciente(),primerHorario,ultimoHorario);
		
		if(pacienteConConsulta) {
			throw new ValidationException("No se puede tener 2 consultas el mismo día");
		}
	}
}
