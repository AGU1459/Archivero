package com.dev.Archivero.servicios;


import com.dev.Archivero.entidades.Editorial;
import com.dev.Archivero.excepciones.MiException;
import com.dev.Archivero.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;
   @Transactional
    public void crearEditorial(String nombreEditorial) throws MiException{
        validar(nombreEditorial);
        Editorial editorial = new Editorial();
        editorial.setNombreEditorial(nombreEditorial);
        editorialRepositorio.save(editorial);

    }
 public List<Editorial> listarEditorial(){
        List<Editorial> editoriales = new ArrayList();
        editoriales =editorialRepositorio.findAll();
        return editoriales;
 }
 public void modificarEditorial(String id, String nombreEditorial) throws MiException{
    validar(nombreEditorial);
     Optional<Editorial> repuesta = editorialRepositorio.findById(id);

     if(repuesta.isPresent()){
         Editorial editorial = repuesta.get();
         editorial.setNombreEditorial(nombreEditorial);

         editorialRepositorio.save(editorial);
     }
 }
    private void validar(String nombreEditorial) throws MiException {

        if( nombreEditorial.isEmpty()|| nombreEditorial == null){
            throw new MiException("El nombreEditorial no puede ser nulo ni estar vacío");
        }
    }
}
