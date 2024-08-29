package com.example.demo.controllers;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping(value = "/all")
    public List<UsuarioDTO> getAll() throws Exception {
        return usuarioService.getAll();
    }

    @GetMapping(value = "/find/{id}")
    public UsuarioDTO find(@PathVariable String id) throws Exception {
        return usuarioService.get(id);
    }

    // Create
    @PostMapping(value = "/save/{id}")
    public ResponseEntity<String> save(@RequestBody Usuario usuario, @PathVariable String id) throws Exception {
        if (id == null || id.length() == 0 || id.equals("null")) {
            id = usuarioService.save(usuario);
        } else {
            usuarioService.save(usuario, id);
        }
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<UsuarioDTO> delete(@PathVariable String id) throws Exception {
        UsuarioDTO persona = usuarioService.get(id);
        if (persona != null) {
            usuarioService.delete(id);
        } else {
            return new ResponseEntity<UsuarioDTO>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<UsuarioDTO>(persona, HttpStatus.OK);
    }
}
