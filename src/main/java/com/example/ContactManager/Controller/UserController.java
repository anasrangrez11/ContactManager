package com.example.ContactManager.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ContactManager.entities.Contact;
import com.example.ContactManager.entities.User;
import com.example.ContactManager.helper.Message;
import com.example.ContactManager.repository.ContactRepository;
import com.example.ContactManager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	ContactRepository contactRepo;
	
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String name = principal.getName();
		System.out.println("-----------------------"+principal.toString()	);
		
		User user = repo.getUserByName(name);
		
		model.addAttribute("user",user);	
	}
	
	@GetMapping("/index")
	public String getDashboard(Model model,Authentication principal) {
			
		return "normal/user-dashboard";
	}
	
	@GetMapping("/addcontactform")
	public String getContactForm(Model model) {
		
		model.addAttribute("title","Add Contact");
		return "normal/contact-form";
	}
	
	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute Contact contact,@RequestParam("profileimage") MultipartFile file, Authentication auth, HttpSession session) {

		try {
		System.out.println("Data : "+contact.toString()
		);
		
		
		if(file.isEmpty()) {
			System.out.println("NO Image in the variable ");
			throw new IOException();
		}else {
			contact.setImage(file.getOriginalFilename());
			
				File newFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(newFile.getAbsolutePath()+File.separator+file.getOriginalFilename());	
				
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is Uploaded");
			
		}
				String name = auth.getName();
		User user = repo.getUserByName(name);
		contact.setUser(user);
		user.getContacts().add(contact);
		this.repo.save(user);
		session.setAttribute("message", new Message("Your Contact is added !!","success"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.setAttribute("Message", new Message("Your Contact is NOt added !! Something went wrong...","danger"));
			e.printStackTrace();
		}
		return "normal/contact-form";
	}
	
	@GetMapping("/viewcontacts/{page}")
	public String showContact(@PathVariable("page") Integer page,Model m,Authentication auth) {
		m.addAttribute("title", "Contacts!");
		
	String name = auth.getName();
	
	User user = repo.getUserByName(name);
	
	Pageable pageable = PageRequest.of(page,5);
	
	Page<Contact> contacts = contactRepo.getContactByUser(user.getId(),pageable);
	
	m.addAttribute("contacts",contacts);
	m.addAttribute("currentpage",page);
	m.addAttribute("totalpage",contacts.getTotalPages());
	
		
		return "normal/show-contacts";
	}
	@GetMapping("/contact/{cid}")
	public String getSingleContact(@PathVariable("cid") Integer cId,Model model,Principal principal) {
		String name = principal.getName();
		
		User user = this.repo.getUserByName(name);
		
		
		Optional<Contact> contactoptional=this.contactRepo.findById(cId);
		Contact contact = contactoptional.get();
		
	

		if(user.getId()==contact.getUser().getId()) {
				model.addAttribute("contact",contact);
		}
				return "normal/ContactDetail";
		
	}
	
	@GetMapping("/deletecontact/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId,Model model,HttpSession session) {
		Optional<Contact> contactoptional = this.contactRepo.findById(cId);
		Contact contact = contactoptional.get();
		contact.setUser(null);
		this.contactRepo.delete(contact); 
		session.setAttribute("message", new Message("Contact deleted successfully","success") );
		return "redirect:/user/viewcontacts/0";
	}
	
	
	
}
