package com.punk.work.task;

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

@Controller
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public String get(Model model) {
        model.addAttribute("tasks", taskService.findAll());

        return "task";
    }

    @DeleteMapping("/task/{id}")
    @ResponseBody
    public JsonResponse delete(@PathVariable("id") String id) {
        JsonResponse response = new JsonResponse();
        Boolean deleted;
        try {
            taskService.deleteById(Integer.parseInt(id));
            deleted = true;
        } catch (IllegalArgumentException e) {
            response.message = "Error: " + e.getMessage();
            deleted = false;
        }

        if(deleted) {
            response.status = "success";
            response.message = "Successfully deleted the task!";
        } else {
            response.status = "error";
        }

        return response;
    }

    @PutMapping("/task")
    @ResponseBody
    public JsonResponse update(@RequestBody Task task) {

        Task savedTask = taskService.save(task);
        JsonResponse response = new JsonResponse();

        if(savedTask != null) {
            response.status = "success";
            response.message = "Successfully updated the task!";
        } else {
            response.status = "error";
            response.message = "Failed to update the task!";
        }

        return response;
    }

    @PostMapping("/task")
    @ResponseBody
    public JsonResponse insert(@RequestBody Task task) {

        Task savedTask = taskService.save(task);
        JsonResponse response = new JsonResponse();

        if(savedTask != null) {
            response.status = "success";
            response.message = "Successfully added the task!";
        } else {
            response.status = "error";
            response.message = "Failed to add the task!";
        }

        return response;
    }
}
