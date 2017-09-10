package guru.springframework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guru.springframework.dao.DogRepository;
import guru.springframework.model.Dog;
import guru.springframework.service.DogService;

@Controller
public class DogController {

	@Autowired
	private DogRepository dogrepository;

	@Autowired
	private DogService dogservice;

	private ArrayList<Dog> dogModelList;

	private List<String> dogrisklist = null;

	@GetMapping(value = "/")
	public String doghome(
			@RequestParam(value = "search", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date q,
			Model model) {
		if (q != null) {
			dogModelList = new ArrayList<Dog>();
			System.out.println("q is = " + q);
			dogrisklist = dogservice.atriskdogs(q);
			for (String name : dogrisklist) {
				System.out.println("Dogs in repository are : " + dogrepository.findAll());
				Dog doggy = dogrepository.findByName(name);
				System.out.println(doggy.toString() + "doggy name : " + doggy.getName());
				dogModelList.add(doggy);
				System.out.println("This dog's name is : " + doggy.getName());
			}
		}
		model.addAttribute("search", dogModelList);

		model.addAttribute("dogs", dogrepository.findAll());

		return "index";

	}

	@PostMapping(value = "/")
	public String adddog(@RequestParam("name") String name,
			@RequestParam("rescued") @DateTimeFormat(pattern = "yyyy-MM-dd") Date rescued,
			@RequestParam("vaccinated") Boolean vaccinated, Model model) {
		dogservice.addADog(name, rescued, vaccinated);
		System.out.println("name = " + name + ",rescued = " + rescued + ", vaccinated = " + vaccinated);
		return "redirect:/";
	}

	@PostMapping(value = "/delete")
	public String deleteDog(@RequestParam("name") String name,
			@RequestParam("id") Long id) {
		dogservice.deleteADOG(name, id);
		System.out.println("Dog named = " + name + "was removed from our database. Hopefully he or she was adopted.");
		return "redirect:/";

	}
	
	@PostMapping(value = "/genkey")
	public String genkey(@RequestParam("name") String name,
			@RequestParam("rescued") @DateTimeFormat(pattern = "yyyy-MM-dd") Date rescued,
			@RequestParam("vaccinated") Boolean vaccinated, Model model) {
		dogservice.getGeneratedKey(name, rescued, vaccinated);
		System.out.println("name = " + name + ",rescued = " + rescued + ", vaccinated = " + vaccinated);
		return "redirect:/";
	}

}
