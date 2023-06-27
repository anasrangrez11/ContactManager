package com.example.ContactManager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ContactManager.entities.User;
import com.example.ContactManager.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bcryptencoder;
	
	@Autowired
	private UserRepository userrepo;
	@RequestMapping("/")
	public String homePage() {
		System.out.println("Home page");
		return "home";
	}
	
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("title", "About | contact manager");
		System.out.println("abput page");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("title", "SignUp | contact manager");
		model.addAttribute("user", new User());
		System.out.println("Sign Up page");
		return "signup";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,RedirectAttributes redirectattr) throws Exception {
		System.out.println(agreement+"-----------------------------"+result1.toString());
		try {
			 if(!agreement) {System.out.println("you have not accepted terms and conditions");
			 	throw new Exception("you have not accepted terms and conditions");
			 }
			 
			 if(result1.hasErrors()){
				 System.out.println("---------ERROR--------------"+result1.toString());
				 model.addAttribute("user", user);
				return "signup ";
			 }			 
			 user.setRole("ROLE_USER");
			 user.setEnable(true);
			 user.setImage("default.png");
			 user.setPassword(bcryptencoder.encode(user.getPassword()));
			 model.addAttribute("user",user);
			 User result = this.userrepo.save(user);
			 System.out.println("--------------------------------------"+result);
			 model.addAttribute("user",new User());
			 redirectattr.addFlashAttribute("message", "Successfully Registered !!");
			 return "redirect:/signup";
			 }catch(Exception e) {
				 e.printStackTrace();
				 model.addAttribute("user",user);
				 redirectattr.addFlashAttribute("message", "Something went wrong !"+e.getMessage());
				 return "redirect:/signup";
			 }
		
	}
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title","SignIn | contact Manager ");
		return "login";
		
	}
	
	
}
