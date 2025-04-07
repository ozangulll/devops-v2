package org.sau.devopsv2.controller;
import org.sau.devopsv2.dto.TaskerDTO;
import org.sau.devopsv2.service.TaskerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskers")
public class TaskerController {

    @Autowired
    private TaskerService taskerService;

    // Görev atama
    @PostMapping("/assign")
    public ResponseEntity<TaskerDTO> assignTask(@RequestBody TaskerDTO dto) {
        TaskerDTO assigned = taskerService.assignTask(dto);
        return ResponseEntity.ok(assigned);
    }

    // Tüm atamaları listele
    @GetMapping
    public ResponseEntity<List<TaskerDTO>> getAllAssignments() {
        return ResponseEntity.ok(taskerService.getAllAssignments());
    }

    // Belirli bir atamayı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        taskerService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
