package com.lostandfound_wc.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception{
        System.out.println("Loading data . . .");
        //roleRepository.save(new Role("USER"));
        //roleRepository.save(new Role("ADMIN"));

        Role role = new Role();
        role.setRole("USER");
        roleRepository.save(role);

        role = new Role();
        role.setRole("ADMIN");
        roleRepository.save(role);




       // Role adminRole=roleRepository.findByRole("ADMIN");
        //Role userRole=roleRepository.findByRole("USER");

        User user=new User("admin@admin.com","beastmaster","Dave","Wolf",true,"DaveWolf");
        Role adminRole=roleRepository.findByRole("ADMIN");
        userRepository.save(user);

        //user.setRoles(Arrays.asList(adminRole));



    }
}
