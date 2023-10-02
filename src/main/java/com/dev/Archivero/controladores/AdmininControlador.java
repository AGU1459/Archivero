package com.dev.Archivero.controladores;

import com.dev.Archivero.entidades.Usuario;
import com.dev.Archivero.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdmininControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

}
