package med.voll.api.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {

	
	public void validar(DatosAgendarConsulta datos) {
		var domingo = DayOfWeek.SUNDAY.equals(datos.fecha());
		var antesDeApertura = datos.fecha().getHour() < 7;
		var despuesDeCierre = datos.fecha().getHour() > 19;
		if (domingo || antesDeApertura || despuesDeCierre) {
			throw new ValidationException("El horario de la clinica es de Lunes a sábados 7 a 19 hs");
		}

	}
}
