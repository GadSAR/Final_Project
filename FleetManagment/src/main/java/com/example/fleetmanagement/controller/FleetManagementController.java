package com.example.fleetmanagement.controller;

import com.example.fleetmanagement.service.FleetManagementServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FleetManagementController {

    FleetManagementServiceInterface studentService;

    @Autowired
    public FleetManagementController(FleetManagementServiceInterface fleetManagementServiceInterface) {
        this.studentService = fleetManagementServiceInterface;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/", "/index", "/home"})
    public String getIndex(Model model) {
        model.addAttribute("title", "FleetManagement System");
        model.addAttribute("title_link_1", "Go to Data Table");
        return "index";
    }


    @GetMapping("/getData")
    public String getAllStudents(Model model) {
        model.addAttribute("all_data", studentService.getAllStudents());
        model.addAttribute("title", "Student Management System");
        return "allInfo";
    }

    /*
call to create a new student, this will call addStudent.html, which
on return from this html file, it will call /students which is  "public String saveStudent"
which is a method in the StudentController.class (see below),
then "public String saveStudent" method will get the new entered student info and also the image file,
and it will call the studentService.saveStudent(student, file); to save the new student

    @GetMapping("/students/new")
    public String addNewStudentForm(Model model) {
        model.addAttribute("title", "FleetManagement System");
        model.addAttribute("title_link_1", "Go to student list");
        model.addAttribute("new_student", "yes");
        Info info = new Info();
        model.addAttribute("student", info);
        return "addStudent";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Info info,
                              @RequestParam("multiPartFile") MultipartFile file) {
        studentService.saveStudent(info, file);
        // redirect to url getStudents - see above ....@GetMapping("/getStudents") function
        return "redirect:/getStudents";
    }
    //********************************************************

    /*
    call to update a student, this will call edit_student.html, which
    on return from this html file, it will call /students{id} which is  "public String updateStudent"
    which is a method in the StudentController.class (see below),
    then "public String updateStudent" method will get the updated student id, info and also the image file,
    and it will call the studentService.updateStudent(id, student, file); to save the updated student

    @GetMapping("students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("title", "FleetManagement System");
        model.addAttribute("title_link_1", "Go to student list");
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("new_student", "no");
        return "updateStudent";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Info info,
                                @RequestParam("multiPartFile") MultipartFile file) {

        boolean isANewImageFile = true;

        // if no image file is selected then use the original image file
        if (file.getOriginalFilename().trim().equals(""))
            isANewImageFile = false;
        // else use the new image file
        studentService.updateStudent(id, info, file, isANewImageFile);
        return "redirect:/getStudents";
    }
    //*******************************************************

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/getStudents";
    }
    */

}