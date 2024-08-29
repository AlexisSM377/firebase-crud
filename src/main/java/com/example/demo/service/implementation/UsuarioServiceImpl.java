package com.example.demo.service.implementation;

import com.example.demo.commons.GenericServiceImpl;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.service.UsuarioService;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, UsuarioDTO> implements UsuarioService {


    @Autowired
    private Firestore firestore;

    public UsuarioServiceImpl() {
        super(UsuarioDTO.class);
    }

    @Override
    public CollectionReference getCollection() {
        return firestore.collection("crud-firebase");
    }
}

