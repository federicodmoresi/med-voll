package med.voll.api.controller;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
import med.voll.api.medico.DatosActualizarMedico;
import med.voll.api.medico.DatosListadoMedico;
import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.DatosRespuestaMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	
	@PostMapping
	public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
		Medico medico= medicoRepository.save(new Medico(datosRegistroMedico));
		//Return 201 created
		// URL donde encontrar al medico
		DatosRespuestaMedico datosRespuestaMedico=new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
				new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
						medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
		 URI url=uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaMedico);
		
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(sort = "nombre",size = 10) Pageable paginacion){
		
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
		//return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody  @Valid DatosActualizarMedico datosActualizarMedico) {
		Medico medico=medicoRepository.getReferenceById(datosActualizarMedico.id());
		medico.actualizarDatos(datosActualizarMedico);
		return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
				new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
						medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
		
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarMedico(@PathVariable Long id) {
	Medico medico=medicoRepository.getReferenceById(id);
	medico.desactivarMedico();
	return ResponseEntity.noContent().build();
	}
	
	//	@DeleteMapping("/{id}")
//	@Transactional
//	public void eliminarMedico(@PathVariable Long id) {
//		Medico medico=medicoRepository.getReferenceById(id);
//		medicoRepository.delete(medico);
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
	Medico medico=medicoRepository.getReferenceById(id);
	var datosMedico=new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
			medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
			new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
					medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
	return ResponseEntity.ok(datosMedico);
	}
	
	
}
