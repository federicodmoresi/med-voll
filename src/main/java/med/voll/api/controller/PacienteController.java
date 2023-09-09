package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.DatosListadoMedico;
import med.voll.api.paciente.DatosActualizarPaciente;
import med.voll.api.paciente.DatosListadoPaciente;
import med.voll.api.paciente.DatosRegistroPaciente;
import med.voll.api.paciente.DatosRespuestaPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@PostMapping
	public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder) {
	Paciente paciente =pacienteRepository.save(new Paciente(datosRegistroPaciente));
	DatosRespuestaPaciente datosRespuestaPaciente=new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getDocumento(), paciente.getEmail(),
			paciente.getTelefono(),new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
					paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));
	URI url=uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
	
	return ResponseEntity.created(url).body(datosRespuestaPaciente);
		
			
			
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoPaciente>> listarPacientes(Pageable paginacion) {
		
		return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
		//return pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
	}
	
		
	
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
		Paciente paciente= pacienteRepository.getReferenceById(datosActualizarPaciente.id());
		paciente.actualizarDatos(datosActualizarPaciente);
		
		return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getDocumento(), paciente.getEmail(),
				paciente.getTelefono(),new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
						paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())));
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarPaciente(@PathVariable Long id) {
		Paciente paciente=pacienteRepository.getReferenceById(id);
		paciente.desactivarPaciente();
		return ResponseEntity.noContent().build();
		
		
		//		Paciente paciente=pacienteRepository.getReferenceById(id);
//		pacienteRepository.delete(paciente);
		
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id) {
		Paciente paciente=pacienteRepository.getReferenceById(id);
		DatosRespuestaPaciente datosRespuestaPaciente=new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getDocumento(), paciente.getEmail(),
				paciente.getTelefono(),new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
						paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));
		return ResponseEntity.ok(datosRespuestaPaciente);
		
		
	}
	

}
