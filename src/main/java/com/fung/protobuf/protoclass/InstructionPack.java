package com.fung.protobuf.protoclass;

import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/4/28 15:30
 */
@Component
public class InstructionPack {

    public InstructionProto.Instruction decode(String instruction) {
        InstructionProto.Instruction.Builder builder = InstructionProto.Instruction.newBuilder();
        builder.setInstruction(instruction);
        return builder.build();
    }

    public String encode(InstructionProto.Instruction instruction) {
        return instruction.toString();
    }
}
