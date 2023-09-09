package med.voll.api.consulta.validaciones;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {

	public void validar(DatosAgendarConsulta datos) {
		var ahora = LocalDateTime.now();
		var horaDeConsulta = datos.fecha();
		var diferenciaDe30Min = Duration.between(ahora, horaDeConsulta).toMinutes() < 30;

		if (diferenciaDe30Min) {
			throw new ValidationException("El horario de la clinica es de Lunes a sábados 7 a 19 hs");
		}

	}

}
