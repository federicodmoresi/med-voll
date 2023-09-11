package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.consulta.Consulta;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.Especialidad;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.DatosRegistroPaciente;
import med.voll.api.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;
	@Test
	@DisplayName("deberia retornar nulo cuando el médico se encuentre en consulta con otro paciente en ese horario")
	void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
		
		var proximoLunes10H=LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);

		var medico= registrarMedico("jose","j@gmail.com","12345",Especialidad.CARDIOLOGIA);
		var paciente=registrarPaciente("Antonio","a@gmail.com","35456");
		registrarConsulta(medico,paciente,proximoLunes10H);
		
		
		var medicoLibre=medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
		System.out.println(medicoLibre);
		assertThat(medicoLibre).isNull();
		
	}
	
	private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
		em.persist(new Consulta(medico,paciente,fecha));
	}
	
	private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
		var medico=new Medico(datosMedico(nombre,email,documento,especialidad));
		em.persist(medico);
		return medico;
	}
	
	private Paciente registrarPaciente(String nombre,String email,String documento) {
		var paciente=new Paciente(datosPaciente(nombre,email,documento));
		em.persist(paciente);
		return paciente;
	}
	
	private DatosRegistroMedico datosMedico(String nombre, String email, String documento,Especialidad especialidad) {
		return new DatosRegistroMedico(nombre, email, "123132", documento, especialidad, datosDireccion());
	}
	
	private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
		return new DatosRegistroPaciente(nombre, documento,email,"45656", datosDireccion());
	}
	
	private DatosDireccion datosDireccion() {
		return new DatosDireccion("loca", "azul", "Funes", "1322", "56");
		
	}
	
	
	
	
	
}
