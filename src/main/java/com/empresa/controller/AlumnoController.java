package com.empresa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/rest/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoService service;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Alumno>> lista(){
		//log.info(">>>> lista ");
		List<Alumno> lstAlumno = service.listaAlumno();
		return ResponseEntity.ok(lstAlumno);
	}
	
	
	@GetMapping("/buscarPorDNI/{dni}")
	public ResponseEntity<List<Alumno>> buscar(@PathVariable("dni") String dni) {
		//log.info(">>>> busca por dni : " + dni);
		List<Alumno> lstAlumno = service.listaPorDni(dni);
		if (!CollectionUtils.isEmpty(lstAlumno)) {
			return ResponseEntity.ok(lstAlumno);
		} else {
			//log.info(">>>> buscar por dni - no existen alumnos con ese dni : " + dni);
			return ResponseEntity.badRequest().build();
		}
	}


	@PostMapping
	public ResponseEntity<Alumno> registra(@RequestBody Alumno obj){
		//log.info(">>>> registra  " + obj.getIdAlumno());
		Alumno objSalida = service.insertaActualizaAlumno(obj);
		if (objSalida != null) {
			return ResponseEntity.ok(objSalida);
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Alumno> actualiza(@RequestBody Alumno obj){
		//log.info(">>>> actualiza  " + obj.getIdAlumno());
		Optional<Alumno> optAlumno = service.obtienePorId(obj.getIdAlumno());
		if (optAlumno.isPresent()) {
			Alumno objSalida = service.insertaActualizaAlumno(obj);
			if (objSalida != null) {
				return ResponseEntity.ok(objSalida);
			}else {
				return ResponseEntity.badRequest().build();
			}	
		}else {
			//log.info(">>>> actualiza no existe el id : " + obj.getIdAlumno());
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Alumno> elimina(@PathVariable("id") int idAlumno){
		//log.info(">>>> elimina  " + idAlumno);
		Optional<Alumno> optAlumno = service.obtienePorId(idAlumno);
		if (optAlumno.isPresent()) {
			service.eliminaAlumno(idAlumno);
			return ResponseEntity.ok(optAlumno.get());
		}else {
			//log.info(">>>> elimina no existe el id : " + idAlumno);
			return ResponseEntity.badRequest().build();
		}
	}
	
}
