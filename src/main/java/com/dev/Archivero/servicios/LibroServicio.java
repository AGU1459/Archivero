package com.dev.Archivero.servicios;


import com.dev.Archivero.entidades.Autor;
import com.dev.Archivero.entidades.Editorial;
import com.dev.Archivero.entidades.Libro;
import com.dev.Archivero.excepciones.MiException;
import com.dev.Archivero.repositorios.AutorRepositorio;
import com.dev.Archivero.repositorios.EditorialRepositorio;
import com.dev.Archivero.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
   @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idEditorial, String idAutor) throws MiException {

       validar(isbn,titulo,ejemplares,idEditorial,idAutor);
       Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();


        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        libro.setAlta( new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    public List<Libro> listarLibros (){
        List <Libro>  libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro (MultipartFile archivo, Long isbn, String titulo, Integer ejemplares, String idEditorial, String idAutor) throws MiException{

       validar(isbn,titulo, ejemplares, idEditorial,idAutor);
        Optional<Libro> repuesta = libroRepositorio.findById(isbn);
        Optional<Editorial> repuestaEditorial = editorialRepositorio.findById(idEditorial);
        Optional<Autor> repuestaAutor = autorRepositorio.findById(idAutor);

        Editorial editorial = new Editorial();
        Autor autor = new Autor();


        if(repuestaAutor.isPresent()){
            autor= repuestaAutor.get();


        }

        if(repuestaEditorial.isPresent()){
            editorial = repuestaEditorial.get();

        }

        if(repuesta.isPresent()){
            Libro libro = repuesta.get();
            libro.setTitulo(titulo);
            libro.setEditorial(editorial);
            libro.setAutor(autor);
            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);
        }



    }
    public Libro getOne(Long id){
       return libroRepositorio.getOne(id);
    }

  private void validar(Long isbn, String titulo, Integer ejemplares, String idEditorial, String idAutor) throws MiException {

      if(isbn ==null ){
          throw new MiException("El isbn no puede ser nulo");
      }

      if(titulo.isEmpty() || titulo == null){
          throw new MiException("El titulo no puede ser nulo ni estar vacío");
      }

      if(ejemplares==null){
          throw new MiException("Los ejemplares no puden ser nulo");
      }

      if(idAutor.isEmpty() || idAutor == null){
          throw  new MiException("El idAutor no puede ser nulo ni estar vacío");
      }

      if(idEditorial.isEmpty() || idEditorial == null){
          throw  new MiException("El idEditorial no puede ser nulo ni estar vacío");

      }
   }

}
