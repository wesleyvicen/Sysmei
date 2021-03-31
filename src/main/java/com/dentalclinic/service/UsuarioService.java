package com.dentalclinic.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dentalclinic.dto.LoginDto;
import com.dentalclinic.dto.SessaoDto;
import com.dentalclinic.dto.TokenDto;
import com.dentalclinic.dto.UsuarioDto;
import com.dentalclinic.exceptions.ObjectNotFoundException;
import com.dentalclinic.mapper.UsuarioMapper;
import com.dentalclinic.model.Usuario;
import com.dentalclinic.repository.UsuarioRepository;
import com.dentalclinic.security.JWTUtil;
import com.dentalclinic.security.UserSS;

/**
 * @author B�rbara Rodrigues, Gabriel Botelho, Guilherme Cruz, Lucas Caputo,
 *         Renan Alencar, Wesley Vicente
 *
 */
@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JWTUtil jwtUtil;
	
	// encripta a senha digitada pelo usuário
	@Autowired
	private BCryptPasswordEncoder pe;

	@Transactional
	public UsuarioDto addUsuario(UsuarioDto usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setCpf(usuarioDto.getCpf());
		usuario.setLogin(usuarioDto.getLogin());
		usuario.setNome(usuarioDto.getNome());
		usuario.setSenha(pe.encode(usuarioDto.getSenha()));
		if (!existsUsuarioWithLogin(usuario.getLogin())) {
			usuarioRepository.save(usuario);
			incluirUsuarioConta(usuario);
			return usuarioDto;
		} else {
			throw new IllegalStateException();
		}
	}

	@Transactional
	private void incluirUsuarioConta(Usuario usuario) {		

		usuarioRepository.save(usuario);

	}

	@Transactional
	public Usuario getUsuarioWithLoginAndSenha(String login, String senha) {
		Usuario usuario = getUsuarioWithLogin(login);
		if(usuario == null) {
			throw new IllegalArgumentException("Usuário não existe");
		}
		else if(pe.matches(senha, usuario.getSenha())) {
			return usuario;
		}
		else {
			throw new IllegalArgumentException("Senha incorreta!");
		}
	}
	
	@Transactional
	public Usuario getUsuarioWithLogin(String login) {
		return usuarioRepository.getUsuarioWithLogin(login);
	}

	@Transactional
	public boolean existsUsuarioWithLogin(String login) {
		Usuario usuario = usuarioRepository.getUsuarioWithLogin(login);
		if (usuario != null) {
			return true;
		} else {
			return false;
		}
			
	}

	@Transactional
	public Usuario search(Integer id) {
		Optional<Usuario> obj = usuarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não encontrado! ID: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	@Transactional
	public List<UsuarioDto> findAll(Integer id) {
		List<Usuario> list = usuarioRepository.findUsuarioAndConta(id);
		return list.stream().map(x -> new UsuarioDto(x)).collect(Collectors.toList());
	}

	@Transactional
	public SessaoDto logar(LoginDto loginDto) {
		SessaoDto sessaoDto = new SessaoDto();

		UsuarioMapper usuarioMapper = new UsuarioMapper();				
		Usuario usuario = getUsuarioWithLoginAndSenha(loginDto.getUsuario(), loginDto.getSenha());		
		
		if(usuario == null) {
			throw new IllegalArgumentException("Usuário ou senha errados!");
		}
		
		TokenDto tokenDto = jwtUtil.getToken(loginDto.getUsuario());
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String dataInicio = agora.format(formatter);
		String dataFim = agora.plusMinutes(60).format(formatter);
		sessaoDto.setToken(tokenDto.getToken());
		sessaoDto.setDataInicio(dataInicio);
		sessaoDto.setDataFim(dataFim);

		sessaoDto.setUsuario(usuarioMapper.getUsuarioDtoFromEntity(usuario));

		return sessaoDto;
	}	

	/***
	 * 
	 * @return
	 */
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}