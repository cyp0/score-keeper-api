package com.score_keeper.controllers;


import com.score_keeper.models.ERole;
import com.score_keeper.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/apii")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/roles")
    public Map<String, Object> getRoles(){
        HashMap<String, Object> response = new HashMap<>();
        try{
            response.put("Roles", roleRepository.findAll());
            return  response;
        }catch (Exception e){
            response.put("error" , e);
            return  response;

        }
    }

    @GetMapping(value = "/roles/{name}")
    public Map<String, Object> getRolesByName(@PathVariable("name") String nombre){
        HashMap<String, Object> response = new HashMap<>();
        try{
            response.put("Roles2", roleRepository.findByName(ERole.ROLE_MODERATOR));
            return  response;
        }catch (Exception e){
            response.put("error" , e);
            return  response;

        }
    }
}
