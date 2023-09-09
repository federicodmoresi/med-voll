package med.voll.api.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import med.voll.api.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

import med.voll.api.paciente.PacienteRepository;

@Service
public class AgendaDeConsultaService {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;	
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	List<ValidadorDeConsultas> validadores;

	public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
		
		if(!pacienteRepository.findById(datos.idPaciente()).isPresent()) {
			throw new ValidacionDeIntegridad("Este ID para el paciente no fue encontrado");
			
		}
		
		if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())) {
			throw new ValidacionDeIntegridad("Este ID para el medico no fue encontrado");
		}
		
		validadores.forEach(v->v.validar(datos));
		
		var paciente= pacienteRepository.findById(datos.idPaciente()).get();
		var medico= seleccionarMedico(datos);
		
		if(medico==null) {
			throw new ValidacionDeIntegridad("No existe médico disponible en este horario y especialidad");
			
		}
		
		var consulta = new Consulta(medico, paciente, datos.fecha());
		
		consultaRepository.save(consulta);
		
		return new DatosDetalleConsulta(consulta);
	}

	private Medico seleccionarMedico(DatosAgendarConsulta datos) {
		if(datos.idMedico()!=null) {
			return medicoRepository.getReferenceById(datos.idMedico());
		}
		
		if(datos.especialidad()==null) {
			throw new ValidacionDeIntegridad("Debe seleccionar una especialidad");
		}
		
		return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
	}

}
