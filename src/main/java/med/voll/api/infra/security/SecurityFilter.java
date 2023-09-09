package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.usuarios.UsuarioRepository;


@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		System.out.println("El filtro esta llamado");
		//Obtener token de headers
		
		var authHeader=request.getHeader("Authorization");
		if(authHeader!=null) {
			var token= authHeader.replace("Bearer ", "");
			System.out.println(token);
			System.out.println(tokenService.getSubject(token));
			var nombreUsuario=tokenService.getSubject(token);
			if (nombreUsuario !=null) {
				//token valido
				var usuario=usuarioRepository.findByLogin(nombreUsuario);
				var authentication=new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities()); //formzamos inicio de sesion
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
 
		filterChain.doFilter(request, response);
	}
	
}
