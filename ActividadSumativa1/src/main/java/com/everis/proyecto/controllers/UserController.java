package com.everis.proyecto.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.proyecto.models.User;
import com.everis.proyecto.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "login.jsp";
	}
	
	@RequestMapping("/registro")
	public String registro(@ModelAttribute("user") User user) {
		return "Registro.jsp";
	}
	
	@RequestMapping("/ingresar")
	public String ingresar(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session
			) {
		boolean exiteUsuario = userService.validarUser(email, password);
		if(exiteUsuario) {
			User user = userService.findByEmail(email);
			//guardando un elemento en session
			session.setAttribute("userId", user.getId());
			return "registro.jsp";
		}
		
		return "redirect:/login";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Valid @ModelAttribute("user") User user) {
		//llamar a las validaciones
		userService.save(user);
		return "login.jsp";
	}
	
	@RequestMapping("/venta")
	public String home(HttpSession session){
		//valida el acceso a rutas
		if(session.getAttribute("userId")!=null) {
			return "registro.jsp";
		}
		return "redirect:/login";
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		if(session.getAttribute("userId")!=null) {
			session.invalidate();
		}
		return "redirect:/login";
	}
	
	

}

