package com.lostandfound_wc.demo;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ItemRepository itemRepository;



    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String showIndex(Model model){
        model.addAttribute("items",itemRepository.findAllByItemCategoryContainingIgnoreCase("Lost"));
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String showAddItemForm(Model model){
        model.addAttribute("item",new Item());
        return"/add";
    }

    @PostMapping("/process")
    public String processItem(@Valid @ModelAttribute("item") Item item,BindingResult result){

        if (result.hasErrors()) {
            return "itemform";
        }
        itemRepository.save(item);
        return "redirect:/list";
    }


    @RequestMapping("/list")
    public String listItems(Model model){
        model.addAttribute("items",itemRepository.findAllByItemCategoryContainingIgnoreCase("Yes"));
        return"list";
    }
    @RequestMapping("/currentlist")
    public String currentListings(Model model){
        model.addAttribute("items",itemRepository.findAll());
        return"currentlist";
    }
    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable("id")long id, Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        return "showitemdetails";
    }

    @RequestMapping("/update/{id}")
    public String updateDetail(@PathVariable("id")long id,Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        return "add";
    }

    @RequestMapping("/found/{id}")
    public String foundItem(@PathVariable("id") long id,Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("Yes");

        //String roomRentMessage="\""+room.getAddress()+"\""+" is rented";

        //redirectAttributes.addFlashAttribute("message1", roomRentMessage);

        model.addAttribute("anItem", itemRepository.findOne(id));
        itemRepository.save(item);
        return "redirect:/list";
    }
    @RequestMapping("/lost/{id}")
    public String lostItem(@PathVariable("id") long id,Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("No");

//        String roomRentMessage="\""+room.getAddress()+"\""+" is available";
//
//        redirectAttributes.addFlashAttribute("message1", roomRentMessage);

        model.addAttribute("anItem", itemRepository.findOne(id));
        itemRepository.save(item);
        return "redirect:/list";
    }

    //For user registration
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }


    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("User") User user, BindingResult result, Model model){
        model.addAttribute("user",user);
        if(result.hasErrors()){
            return "registration";
        }else{
            userService.saveUser(user);
            model.addAttribute("message","User Account Successfully Created");
        }
        return "index";
    }
}
