package med.voll.api.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String documento;
	private String email;
	private String telefono;
	@Embedded
	private Direccion direccion;
	private boolean activo;

	public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
		this.nombre = datosRegistroPaciente.nombre();
		this.documento = datosRegistroPaciente.documento();
		this.email = datosRegistroPaciente.documento();
		this.telefono = datosRegistroPaciente.telefono();
		this.direccion = new Direccion(datosRegistroPaciente.direccion());
		this.activo=true;
	}

	public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente) {
		if (datosActualizarPaciente.nombre() != null) {
			this.nombre = datosActualizarPaciente.nombre();
		}

		if (datosActualizarPaciente.direccion() != null) {
			this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
		}

		if (datosActualizarPaciente.documento() != null) {
			this.documento = datosActualizarPaciente.documento();
		}

	}

	public void desactivarPaciente() {
		this.activo=false;
		
	}

}
