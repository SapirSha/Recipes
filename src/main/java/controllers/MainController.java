package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Others.User;

@Controller
public class MainController {

	@RequestMapping("/")
	public String showPage() {
		System.out.println("HELLOO __ _ _ _ _");
		return "MainPage";
	}

    @RequestMapping("/help")
	public String signUp(@ModelAttribute("user") User user, HttpServletRequest request) {
		System.out.println("HEREAWSD");
		return "WASD";
	}

}
