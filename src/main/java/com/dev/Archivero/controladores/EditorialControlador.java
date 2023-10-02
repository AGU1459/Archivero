package com.dev.Archivero.controladores;


import com.dev.Archivero.entidades.Editorial;
import com.dev.Archivero.excepciones.MiException;
import com.dev.Archivero.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, ModelMap modelo) throws MiException {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito","La Editorial se cargo con exito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            return "editorial_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public  String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditorial();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
}