/**
 * 
 */
package com.spring.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tctav.demo.model.entity.Contact;

/**
 * @author hiennt
 *
 */
@Controller
@SessionAttributes
public class ContactController {
	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result) {
		// write the code here to add contact
		return "redirect:contact";
	}

	@RequestMapping("/contact")
	public ModelAndView showContacts() {
		return new ModelAndView("contact", "command", new Contact());
	}
	
//	@RequestMapping(value = "/contact", method = RequestMethod.GET)
//	public String showContacts() {
//		return "contact";
//	}
}
