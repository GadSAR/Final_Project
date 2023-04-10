package com.example.ifm.controller;

import com.example.ifm.model.Obd2Data;
import com.example.ifm.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class BackendController {

    private BackendService backendService;

    @Autowired
    public BackendController(BackendService backendService) {
        this.backendService = backendService;
    }

    @PostMapping
    public Obd2Data saveEmployee(@RequestBody Obd2Data obd2Data) {
        System.out.println("save");
        return backendService.saveObd2Data(obd2Data);
    }

    @GetMapping
    public List<Obd2Data> getAllEmployee() {
        return backendService.getAllObd2Data();
    }

    @GetMapping("/{id}")
    public Optional<Obd2Data> getEmployeeById(@PathVariable Long id) {
        return backendService.getObd2DataById(id);
    }

    @PutMapping("/{id}")
    public Obd2Data updateEmployee(@PathVariable Long id, @RequestBody Obd2Data obd2Data) {
        System.out.println("update");
        return backendService.updateObd2Data(id, obd2Data);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        backendService.deleteStudent(id);
    }
}
