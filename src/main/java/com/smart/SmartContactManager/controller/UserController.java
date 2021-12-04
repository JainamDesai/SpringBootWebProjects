package com.smart.SmartContactManager.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.SmartContactManager.entity.Contacts;
import com.smart.SmartContactManager.entity.User;
import com.smart.SmartContactManager.repo.ContactRepo;
import com.smart.SmartContactManager.repo.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private ContactRepo contactrepo;
	
	
	@ModelAttribute
	public void addCommanData(Model model,Principal principal) {
		User user = userrepo.findByEmail(principal.getName());
		if(user!=null) {
			model.addAttribute("user", user);
			model.addAttribute("title", "DashBoard");
		}
		
		
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model,Principal principal) {
		
		model.addAttribute("title", "Dashboard");
		return "/normal/DashBoard";
	}
	//add contact form
	@GetMapping("/addContacts")
	public String addContactForm(Model model) {
		model.addAttribute("contact", new Contacts());
		model.addAttribute("title", "Add Contact Form");
		return "/normal/AddContacts";
		
		
	}
	
	@RequestMapping(value = "/process-contact",method = RequestMethod.POST)
	public String processContact(Model model, @ModelAttribute Contacts contact,Principal principal) {
		System.out.println("=================="+contact.getEmail());
		Contacts contactobj = contactrepo.findByEmail(contact.getEmail());
		
		if(contactobj!=null) {
			contactobj.setEmail(contact.getEmail());
			contactobj.setDescription(contact.getDescription());
			contactobj.setName(contact.getName());
			contactobj.setNickname(contact.getNickname());
			contactobj.setPhoneString(contact.getPhoneString());
			contactobj.setSecondName(contact.getSecondName());
			contactrepo.save(contactobj);
			model.addAttribute("contact", contactobj);
			return "redirect:/user/viewAllContact";
			
		} else {
		String username = principal.getName();
		User user = userrepo.findByEmail(username);
		contact.setUser(user);
		Contacts c = contactrepo.save(contact);
		if(c!=null) {
		model.addAttribute("contact", new Contacts());
		model.addAttribute("title", "Add Contact Form");
		model.addAttribute("success", "Contact saved successfully");
		return "/normal/AddContacts";
		} else {
			model.addAttribute("contact", contact);
			model.addAttribute("title", "Add Contact Form");
			model.addAttribute("error", "Contact saved failed");
			return "/normal/AddContacts";	
		  }
		}	
		
	}

	@RequestMapping(value = "/viewAllContact",method = RequestMethod.GET)
	public String viewAllContacts(Model model, Principal principal) {
		String username = principal.getName();
		User user = userrepo.findByEmail(username);
		
		List<Contacts> contectlist = null;
		contectlist = contactrepo.getcontactlist(user.getId());
	
		model.addAttribute("title", "View All Contact");
		model.addAttribute("users", contectlist);	
		return "/normal/ViewContacts";
	}
	
	@RequestMapping(value = "/updateContact" , method = RequestMethod.GET)
	public String updateContact(@RequestParam("contact_id") int contact_id,Model model) {
		
		Contacts contact = null;
		Optional<Contacts> contactop = contactrepo.findById(contact_id);
		if(contactop.isPresent()) {
			
			contact = contactop.get();
		}
		
		model.addAttribute("title", "Add Contact Form");
		model.addAttribute("contact", contact);
		return "/normal/AddContacts";
		
		
	}
	
	@RequestMapping(value = "/deleteContact",method = RequestMethod.GET)
	public String deleteContact(@RequestParam("contact_id")int contact_id) {
		contactrepo.deleteById(contact_id);
		return "redirect:/user/viewAllContact";	
	}
	
	@RequestMapping(value = "/profile")
	public String getUserProfile(Model model,Principal principal) {
		
		String username = principal.getName();
		User user = userrepo.findByEmail(username);
		model.addAttribute("title", "Profile");
		model.addAttribute("user", user);
		return "/normal/Profile";
		
		
	}
	
	
	
}
