package com.imse.cookingproject.controller.other;

import com.imse.cookingproject.model.Instruction;
import com.imse.cookingproject.service.otherServices.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("instruction")
public class InstructionController {
    @Autowired
    private InstructionService instructionService;

    @PostMapping("/add")
    public void postInstruction(@RequestBody Instruction instruction) {
        instructionService.saveToDatabase(instruction);
    }

    @PostMapping("/dropInstructions")
    public void dropTable() {
        instructionService.dropTable();
    }

    @GetMapping("/listInstructions")
    public List<Instruction> getRecipes() {
        return instructionService.getInstructionsList();
    }

    @PutMapping("/deleteInstruction/{instruction_id}")
    public void deleteInstruction(@PathVariable int instruction_id) {
        instructionService.deleteInstruction(instruction_id);
    }

    @PostMapping("/generateData")
    public void generateData() {
        instructionService.generateData();
    }
}
