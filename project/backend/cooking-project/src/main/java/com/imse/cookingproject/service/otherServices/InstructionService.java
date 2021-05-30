package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.Instruction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class InstructionService {
    private final Instruction instruction = new Instruction();

    public void saveToDatabase(Instruction instruction) {
        instruction.saveInsertToDatabase();
    }

    public void dropTable() {
        instruction.dropTable();
    }

    public List<Instruction> getInstructionsList() {
        return Instruction.returnList();
    }

    public void deleteInstruction(int instruction_id) {
        Instruction.deleteInstruction(instruction_id);
    }

    public void generateData() {
        Instruction.generateData();
    }
}
