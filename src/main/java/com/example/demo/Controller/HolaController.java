/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Controller;

import com.example.demo.Model.Cliente;
import com.example.demo.Model.Usuario;
import com.example.demo.Repositorio.ClienteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author usuario
 */
@Controller
public class HolaController {

    private List<Usuario> usuarios = new ArrayList();
    private List<Object> usuarios2 = new ArrayList();
    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping("/hola")
    public String hola(@RequestParam(value = "nombre", required = false, defaultValue = "Mundo") String nombre, Model model) {
        nombre = "2222";
        model.addAttribute("nombre", nombre);
        return "View/login";
    }

    /*
    @RequestMapping("/login", metho)
    public String login(@RequestParam(value = "nombre", required = false, defaultValue = "Mundo") String nombre, Model model) {
        nombre = "2222";
        model.addAttribute("nombre", nombre);
        return "View/login";
    }
     */
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("nombre", "APP de Gestión");
        return "View/login";
    }

    @GetMapping("/newuser")
    public String nuevousuario(Model model) {
        model.addAttribute("usuarios", this.usuarios2);

        return "newuser";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String crearusuario(@ModelAttribute Usuario usuario, Model model) {
        this.usuarios.add(usuario);
        System.out.println(usuario.toString());
        model.addAttribute("result", true);
        model.addAttribute("usuarios", usuarios);
        return "newuser";
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String crearusuario(@RequestParam(name = "id") int id, Model model) {
        Usuario user = this.usuarios.get(id);
        user.setId(id);
        model.addAttribute("user", user);
        model.addAttribute("usuarios", this.usuarios);
        return "edituser";
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String crearusuario(@ModelAttribute Usuario usuario, @RequestParam(name = "id") int id, Model model) {
        System.out.println(usuario.toString());
        System.out.println(id);
        Usuario user = this.usuarios.get(id);
        user.setId(id);
        model.addAttribute("user", user);
        model.addAttribute("usuarios", this.usuarios);
        return "edituser";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam(name = "email") String email, @RequestParam(name = "password", required = false) Optional<String> password, Model model) {
        if (email.equals("a@a.com") && password.isPresent() && password.get().equals("1234")) {
            model.addAttribute("usr", email);
            return "main";
        } else {
            model.addAttribute("error", "Error en usuario o contraseña");

            return "View/login";
        }
    }

    @GetMapping("/delete")
    public RedirectView delete(@RequestParam(name = "id") int id) {
        this.usuarios.remove(id);
        return new RedirectView("newuser");
    }

    @PostMapping("/api/newuser")
    public ResponseEntity<List<Usuario>> apinewuser(@RequestBody Usuario user) {
        this.usuarios.add(user);

        System.out.println(user.toString());
        user.setId(this.usuarios.size());
        return new ResponseEntity<>(this.usuarios, HttpStatus.OK);
    }

    @GetMapping("/api/search")
    public ResponseEntity<Object> apisearcha(@RequestParam String text) {
        Usuario usuario = this.usuarios.stream().filter(user -> text.equals(user.getNombre())).findAny().orElse(null);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

     @GetMapping("/api/clientes")
    public ResponseEntity<Object> apicliente() {        
        List<Cliente> clientes=(List<Cliente>) clienteRepository.findAll();   
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

}
