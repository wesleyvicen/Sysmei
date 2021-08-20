package com.sysmei.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sysmei.dto.NewPacienteDTO;
import com.sysmei.dto.PacienteDTO;
import com.sysmei.keys.ParamsKeys;
import com.sysmei.keys.RotasKeys;
import com.sysmei.model.Paciente;
import com.sysmei.service.impl.DocumentsServiceImpl;
import com.sysmei.service.impl.PacienteServiceImpl;
import com.sysmei.service.impl.S3ServiceImpl;

@RestController
@RequestMapping(value = RotasKeys.PACIENTE)
public class PacienteController {

	@Autowired
	private PacienteServiceImpl pacienteService;

	@Autowired
	private S3ServiceImpl s3Service;

	@Autowired
	private DocumentsServiceImpl documentsService;

	/**
	 * 
	 * @param login
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET, params = { ParamsKeys.LOGIN })
	public ResponseEntity<List<PacienteDTO>> findAll(@RequestParam(name = ParamsKeys.LOGIN) String login) {
		List<Paciente> list = pacienteService.getPacientesWithLogin(login);
		List<PacienteDTO> listDto = list.stream().map(PacienteDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(RotasKeys.ID)
	public ResponseEntity<Paciente> getById(@PathVariable Long id) {
		Paciente dto = pacienteService.getById(id);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * 
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	@RequestMapping(value = RotasKeys.PAGE, method = RequestMethod.GET)
	public ResponseEntity<Page<PacienteDTO>> findPage(
			@RequestParam(value = RotasKeys.PAGE, defaultValue = "0") Integer page,
			@RequestParam(value = ParamsKeys.LINES_PER_PAGE, defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = ParamsKeys.ORDER_BY, defaultValue = "nome") String orderBy,
			@RequestParam(value = ParamsKeys.DIRECTION, defaultValue = "ASC") String direction) {
		Page<Paciente> list = pacienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<PacienteDTO> listDto = list.map(PacienteDTO::new);
		return ResponseEntity.ok().body(listDto);
	}

	/**
	 * 
	 * @param id
	 * @param file
	 * @return
	 */

	@RequestMapping(value = RotasKeys.PACIENTE_ID, method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long id,
			@RequestParam(name = ParamsKeys.FILE) MultipartFile file) {
		URI uri = pacienteService.uploadProfilePicture(id, file);
		return ResponseEntity.created(uri).build();
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody NewPacienteDTO dto) {
		Paciente paciente = pacienteService.fromDTO(dto);

		return new ResponseEntity<>(pacienteService.insert(paciente), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param objDto
	 * @param id
	 * @return
	 */

	@RequestMapping(value = RotasKeys.ID, method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody NewPacienteDTO objDto, @PathVariable Long id) {
		Paciente obj = pacienteService.fromDTO(objDto);
		obj.setId(id);
		obj = pacienteService.update(obj);
		return ResponseEntity.noContent().build();

	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = RotasKeys.ID, method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		pacienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param keyName
	 * @param fileId
	 * @return
	 */

	@RequestMapping(value = RotasKeys.DELETE, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(@RequestParam(value = ParamsKeys.FILE_NAME) String keyName,
			@RequestParam(value = ParamsKeys.FILE_ID) Long fileId) {
		s3Service.deleteFile(keyName);
		documentsService.delete(fileId);
		final String response = "[" + keyName + "] detelado com sucesso.";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}