package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DatosJWTTokken;
import med.voll.api.infra.security.TokenService;
import med.voll.api.usuarios.DatosAutenticacionUsuario;
import med.voll.api.usuarios.Usuario;

@RestController
@RequestMapping("/login")
public class AutenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity autenticarUsuario (@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
		Authentication authToken=new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());
		var usuarioAutenticado = authenticationManager.authenticate(authToken);
		
		var JWTToken= tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());
		return ResponseEntity.ok(new DatosJWTTokken(JWTToken));
	}
}
