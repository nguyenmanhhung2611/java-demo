package ctcav.com.demo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tctav.com.demo.farm.House;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	// Way 1: houseBean map houseBean in file .xml
	/*@Resource(name="houseBean")
	private House house;*/
	
	// Way 2: house map via class define in file .xml
	@Autowired
	private House house;
	
	@Autowired
	private ArrayList<String> arrayListBean;
	
	@Autowired
	private HashMap<Integer, String> hasmapBean;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("animal1", house.getAnimal1().makeSound());
		model.addAttribute("animal2", house.getAnimal2().makeSound());
		
		System.out.println("Animal 1: " + house.getAnimal1().makeSound());
		System.out.println("Animal 2: " + house.getAnimal2().makeSound());
		System.out.println("===========");
		System.out.println(arrayListBean);
		System.out.println(hasmapBean);
		
		return "home";
	}
	
}
