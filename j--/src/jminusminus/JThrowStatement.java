// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * An AST node for a throw-statement.
 */
class JThrowStatement extends JStatement {
    // The thrown exception.
    private JExpression expr;

    /**
     * Constructs an AST node for a throw-statement.
     *
     * @param line line in which the throw-statement appears in the source file.
     * @param expr the returned expression.
     */
    public JThrowStatement(int line, JExpression expr) {
        super(line);
        this.expr = expr;
    }

    /**
     * {@inheritDoc}
     */
    public JStatement analyze(Context context) {
       
        /* 
        MethodContext methodContext = context.methodContext();
        if (methodContext.methodReturnType() == Type.CONSTRUCTOR) {
            if (expr != null) {
                JAST.compilationUnit.reportSemanticError(line(),
                    "cannot return a value from a constructor");
            }
        } else {
            Type returnType = methodContext.methodReturnType();
	    methodContext.confirmMethodHasReturn();
            if (expr != null) {
                if (returnType == Type.VOID) {
                    JAST.compilationUnit.reportSemanticError(line(),
                        "cannot return a value from a void method");
                } else {
                    expr = expr.analyze(context);
                    expr.type().mustMatchExpected(line(), returnType);
                }
            } else {
                if (returnType != Type.VOID) {
                    JAST.compilationUnit.reportSemanticError(line(),
                        "missing return value");
                }
            }
        }
        */
        return this;
    }


    /**
     * {@inheritDoc}
     */
   public void codegen(CLEmitter output) {
        /*
        if (expr == null) {
            output.addNoArgInstruction(RETURN);
        } else {
            expr.codegen(output);
            if (expr.type() == Type.INT
                || expr.type() == Type.BOOLEAN
                || expr.type() == Type.LONG
                || expr.type() == Type.DOUBLE
                || expr.type() == Type.CHAR) {
                output.addNoArgInstruction(IRETURN);
            } else {
                output.addNoArgInstruction(ARETURN);
            }
        }
        */
    }
    public void codegen(CLEmitter output, String passInOut){
    }


    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JThrowStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Expression", e1);
        expr.toJSON(e1);
    }
}
