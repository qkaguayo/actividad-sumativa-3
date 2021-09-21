package com.everis.proyecto.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.proyecto.models.Producto;
import com.everis.proyecto.services.ProductoService;
import com.everis.proyecto.services.VentaService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ProductoService ps;
	
	@Autowired 
	private VentaService ventaService;
		
		
		@RequestMapping("")
		public String index (@ModelAttribute("producto") Producto producto, Model model) {
			System.out.println("index");
			
			
			List<Producto> lista_productos = ps.findAll();
			model.addAttribute("lista_productos", lista_productos);
			model.addAttribute("lista_ventas", ventaService.findAll());
			
			return "producto.jsp";
		}
		
		@RequestMapping(value="/crear", method = RequestMethod.POST)
		public String crear(@Valid @ModelAttribute("producto") Producto producto) {
			System.out.println("crear"+ producto.getNombre());
			
			ps.insertarProducto(producto);
			return "redirect:/producto";
		}
		
		//METODO ACTUALIZAR
		@RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
		public String actualizar(@PathVariable("id") Long id, Model model) {
		System.out.println("actualizar id: "+ id);
		
		Producto producto = ps.buscarProducto(id);
		
		model.addAttribute("producto", producto);
		model.addAttribute("lista_ventas", ventaService.findAll());
		return "editar_producto.jsp";
		}
		
		@RequestMapping(value="/modificar", method= RequestMethod.PUT)
		public String modificar(@Valid @ModelAttribute("producto") Producto producto) {
			
			System.out.println("el id a modificar es: " + producto.getId());
			ps.modificarProducto(producto);
			
			return "redirect:/producto";
		}
		
		//METODO ELIMINAR
		@RequestMapping(value="/eliminar", method = RequestMethod.POST)
		public String eliminar(@RequestParam("id") Long id) {
			System.out.println("eliminar id: " + id);
			ps.eliminarProducto(id);
			
			return "redirect:/producto";
		}
		
		
		@RequestMapping("/buscar")
		public String buscar() {
			return "producto.jsp";
		}
}
