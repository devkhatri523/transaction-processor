package com.demo.induction.tp.pojo;

import java.util.List;

public class ErrorResponse {
    private List<Violation> violations;
    private boolean isBalanced;

    public ErrorResponse(){}

    public ErrorResponse(List<Violation> violations, boolean isBalanced) {
        this.violations = violations;
        this.isBalanced = isBalanced;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }
}
