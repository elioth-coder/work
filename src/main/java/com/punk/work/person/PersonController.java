package com.punk.work.person;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punk.work.JsonResponse;
import com.punk.work.task.Task;
import com.punk.work.task.TaskService;

@Controller
public class PersonController {
    
    @Autowired
    private PersonService personService;    
    @Autowired
    private TaskService taskService;

    @GetMapping("/person/{id}/tasks")
    public String viewTasks(@PathVariable("id") String id, Model model) {
        Optional<Person> optionalPerson = personService.findById(Integer.parseInt(id));
        List<Task> tasks = taskService.findAllByPersonId(Integer.parseInt(id));
        Person person = (optionalPerson.isPresent()) ? optionalPerson.get() : null;
        
        model.addAttribute("person", person);
        model.addAttribute("tasks", tasks);

        return "person-tasks";
    }

    @GetMapping("/person")
    public String get(Model model) {
        model.addAttribute("persons", personService.findAll());

        return "person";
    }

    @PostMapping("/person")
    @ResponseBody
    public JsonResponse insert(@RequestBody Person person) {

        Person savedPerson = personService.save(person);
        JsonResponse response = new JsonResponse();

        if(savedPerson != null) {
            response.status = "success";
            response.message = "Successfully added the person!";
        } else {
            response.status = "error";
            response.message = "Failed to add the person!";
        }

        return response;
    }

    @PutMapping("/person")
    @ResponseBody
    public JsonResponse update(@RequestBody Person person) {

        Person savedPerson = personService.save(person);
        JsonResponse response = new JsonResponse();

        if(savedPerson != null) {
            response.status = "success";
            response.message = "Successfully updated the person!";
        } else {
            response.status = "error";
            response.message = "Failed to update the person!";
        }

        return response;
    }

    @DeleteMapping("/person/{id}")
    @ResponseBody
    public JsonResponse delete(@PathVariable("id") String id) {
        JsonResponse response = new JsonResponse();
        Boolean deleted;
        try {
            personService.deleteById(Integer.parseInt(id));
            deleted = true;
        } catch (IllegalArgumentException e) {
            response.message = "Error: " + e.getMessage();
            deleted = false;
        }

        if(deleted) {
            response.status = "success";
            response.message = "Successfully deleted the person!";
        } else {
            response.status = "error";
        }

        return response;
    }
}
