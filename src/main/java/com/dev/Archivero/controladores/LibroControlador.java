package com.dev.Archivero.controladores;


import com.dev.Archivero.entidades.Autor;
import com.dev.Archivero.entidades.Editorial;
import com.dev.Archivero.entidades.Libro;
import com.dev.Archivero.excepciones.MiException;
import com.dev.Archivero.servicios.AutorServicio;
import com.dev.Archivero.servicios.EditorialServicio;
import com.dev.Archivero.servicios.LibroServicio;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
    List<Autor> autores = autorServicio.listarAutor();
    List<Editorial> editorial = editorialServicio.listarEditorial();

    modelo.addAttribute("autores", autores);
    modelo.addAttribute("editoriales", editorial);

        return "libro_form.html";
    }
    @PostMapping("/registro")
    public String registro (@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares, @RequestParam String idEditorial, String idAutor, @NotNull ModelMap modelo) throws  MiException{
        try{
            libroServicio.crearLibro(isbn, titulo, ejemplares, idEditorial, idAutor);

            modelo.put("exito", "El libro fue cargado correctamente" );
        } catch (MiException ex){
            List<Autor> autores = autorServicio.listarAutor();
            List<Editorial> editorial = editorialServicio.listarEditorial();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editorial);

            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public  String lista(ModelMap modelo){
        List<Libro> libros = libroServicio.listarLibros();

        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo){
        modelo.put("libro", libroServicio.getOne(isbn));

        List<Autor> autores = autorServicio.listarAutor();
        List<Editorial> editoriales = editorialServicio.listarEditorial();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo, MultipartFile archivo){
        try {
            List<Autor> autores = autorServicio.listarAutor();
            List<Editorial> editoriales = editorialServicio.listarEditorial();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroServicio.modificarLibro(archivo, isbn, titulo, ejemplares, idAutor, idEditorial);


            return "redirect:../lista";

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutor();
            List<Editorial> editoriales = editorialServicio.listarEditorial();

            modelo.put("error", ex.getMessage());

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_modificar.html";
        }
    }
}
