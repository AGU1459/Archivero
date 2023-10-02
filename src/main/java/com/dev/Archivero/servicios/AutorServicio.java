package com.dev.Archivero.servicios;


import com.dev.Archivero.entidades.Autor;
import com.dev.Archivero.excepciones.MiException;
import com.dev.Archivero.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;
 @Transactional
    public void crearAutor(String nombre) throws MiException {
      validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }


    public List<Autor> listarAutor(){
        List<Autor> autores = new ArrayList();
      autores = autorRepositorio.findAll();
      return autores;
    }

    public void modificarAutor(String nombre, String id) throws  MiException{
        Optional<Autor> repuesta = autorRepositorio.findById(id);
          validar(nombre);
        if(repuesta.isPresent()){
            Autor autor = repuesta.get();
            autor.setNombre(nombre);

            autorRepositorio.save(autor);

        }
    }

    public Autor  getOne(String id){
     return autorRepositorio.getOne(id);

    }
    private void validar(String nombre) throws MiException{

     if(nombre.isEmpty() || nombre == null){
         throw new MiException("El nombre no puede ser nulo ni estar vacío");
     }
    }

}