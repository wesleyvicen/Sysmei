package com.sysmei.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sysmei.dto.LoginDto;
import com.sysmei.dto.UsuarioDto;
import com.sysmei.exceptions.ObjectNotFoundException;
import com.sysmei.keys.Keys;
import com.sysmei.model.Usuario;
import com.sysmei.service.UsuarioService;

@RestController
@RequestMapping(value = Keys.USER)
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = Keys.ID, method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		try {
			Usuario obj = usuarioService.search(id);
			return ResponseEntity.ok().body(obj);
		} catch (ObjectNotFoundException e) {
			return new ResponseEntity<>(
					String.format("Usuario de ID %s Não encontrado, por favor tente um ID diferente.", id),
					HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(("Houve algum erro intento, por favor tente mais tarde."),
					HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * 
	 * @param code
	 * @return
	 */

	@RequestMapping(value = Keys.TOKEN, method = RequestMethod.GET)
	public String verificarUser(@RequestHeader(name = Keys.code) String code) {
		if (usuarioService.verificarUser(code)) {
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}

	/**
	 * 
	 * @param usuarioDto
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> addConta(@RequestBody UsuarioDto usuarioDto) {
		try {

			return new ResponseEntity<>(usuarioService.addUsuario(usuarioDto), HttpStatus.CREATED);

		} catch (IllegalStateException e) {
			return new ResponseEntity<>(String.format(
					"Usuario %s já existe no sistema e não pode ser criado, por favor tente um login diferente.",
					usuarioDto.getLogin()), HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(("Houve algum erro intento, por favor tente mais tarde."),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * @param loginDto
	 * @return
	 */

	@PostMapping(value = Keys.LOGIN)
	public ResponseEntity<?> logar(@RequestBody LoginDto loginDto) {
		try {
			return new ResponseEntity<>(usuarioService.logar(loginDto), HttpStatus.OK);

		} catch (IllegalArgumentException ex) {
			return new ResponseEntity<>(String.format("Erro: %s", ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * @param file
	 * @return
	 */

	@RequestMapping(value = Keys.PICTURE, method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = Keys.PARAM_FILE) MultipartFile file) {
		URI uri = usuarioService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

}
