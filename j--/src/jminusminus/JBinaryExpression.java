// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * This abstract base class is the AST node for a binary expression --- an expression with a binary
 * operator and two operands: lhs and rhs.
 */
abstract class JBinaryExpression extends JExpression {
    /**
     * The binary operator.
     */
    protected String operator;

    /**
     * The lhs operand.
     */
    protected JExpression lhs;

    /**
     * The rhs operand.
     */
    protected JExpression rhs;

    /**
     * Constructs an AST node for a binary expression.
     *
     * @param line     line in which the binary expression occurs in the source file.
     * @param operator the binary operator.
     * @param lhs      the lhs operand.
     * @param rhs      the rhs operand.
     */
    protected JBinaryExpression(int line, String operator, JExpression lhs, JExpression rhs) {
        super(line);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JBinaryExpression:" + line, e);
        e.addAttribute("operator", operator);
        e.addAttribute("type", type == null ? "" : type.toString());
        JSONElement e1 = new JSONElement();
        e.addChild("Operand1", e1);
        lhs.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("Operand2", e2);
        rhs.toJSON(e2);
    }
}

/**
 * The AST node for a multiplication (*) expression.
 */
class JMultiplyOp extends JBinaryExpression {
    /**
     * Constructs an AST for a multiplication expression.
     *
     * @param line line in which the multiplication expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JMultiplyOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.STRING || rhs.type() == Type.STRING) {
            return (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else if (lhs.type() == Type.DOUBLE && rhs.type() == Type.DOUBLE) {
            type = Type.DOUBLE;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for *");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if (type == Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IMUL);
        } else if (type == Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LMUL);
        } else if (type == Type.DOUBLE){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DMUL);
        }
    }
}

/**
 * The AST node for a plus (+) expression. In j--, as in Java, + is overloaded to denote addition
 * for numbers and concatenation for Strings.
 */
class JPlusOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an addition expression.
     *
     * @param line line in which the addition expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JPlusOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.STRING || rhs.type() == Type.STRING) {
            return (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
        } 
        else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } 
        else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } 
        else if (lhs.type() == Type.DOUBLE && rhs.type() == Type.DOUBLE) {
            type = Type.DOUBLE;
        } 
        else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for +");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if (type == Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        } 
        else if (type == Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LADD);
        } 
        else if (type == Type.DOUBLE){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DADD);
        }
    }
}

/**
 * The AST node for a subtraction (-) expression.
 */
class JSubtractOp extends JBinaryExpression {
    /**
     * Constructs an AST node for a subtraction expression.
     *
     * @param line line in which the subtraction expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JSubtractOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else if (lhs.type() == Type.DOUBLE && rhs.type() == Type.DOUBLE) {
            type = Type.DOUBLE;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for -");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISUB);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSUB);
        }else if(type==Type.DOUBLE){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DSUB);
        }
    }
}


class JDivideOp extends JBinaryExpression {
    /**
     * Constructs an AST node for a division expression.
     *
     * @param line line in which the division expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JDivideOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else if (lhs.type() == Type.DOUBLE && rhs.type() == Type.DOUBLE) {
            type = Type.DOUBLE;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for /");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IDIV);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LDIV);
        }else if(type==Type.DOUBLE){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DDIV);
        }
        
    }
}

/**
 * The AST node for a remainder (%) expression.
 */
class JRemainderOp extends JBinaryExpression {
    /**
     * Constructs an AST node for a remainder expression.
     *
     * @param line line in which the division expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JRemainderOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else if (lhs.type() == Type.DOUBLE && rhs.type() == Type.DOUBLE) {
            type = Type.DOUBLE;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for %");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IREM);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LREM);
        }else if(type==Type.DOUBLE){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DREM);
        }
    }
}

/**
 * The AST node for an inclusive or (|) expression.
 */
class JOrOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an inclusive or expression.
     *
     * @param line line in which the inclusive or expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
   
    public JOrOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for |");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IOR);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LOR);
        }
    }
}

/**
 * The AST node for an exclusive or (^) expression.
 */
class JXorOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an exclusive or expression.
     *
     * @param line line in which the exclusive or expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JXorOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for ^");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IXOR);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LXOR);
        }
    }
}

/**
 * The AST node for an and (&amp;) expression.
 */
class JAndOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an and expression.
     *
     * @param line line in which the and expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JAndOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for &");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IAND);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LAND);
        }
    }
}

/**
 * The AST node for an arithmetic left shift (&lt;&lt;) expression.
 */
class JALeftShiftOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an arithmetic left shift expression.
     *
     * @param line line in which the arithmetic left shift expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JALeftShiftOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for <<");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISHL);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSHL);
        }
    }
}

/**
 * The AST node for an arithmetic right shift (&rt;&rt;) expression.
 */
class JARightShiftOp extends JBinaryExpression {
    /**
     * Constructs an AST node for an arithmetic right shift expression.
     *
     * @param line line in which the arithmetic right shift expression occurs in the source file.
     
     */
    public JARightShiftOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for >>");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISHR);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSHR);
        }
    }
}

/**
 * The AST node for a logical right shift (&rt;&rt;&rt;) expression.
 */
class JLRightShiftOp extends JBinaryExpression {
    /**
     * Constructs an AST node for a logical right shift expression.
     *
     * @param line line in which the logical right shift expression occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JLRightShiftOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if (lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
            type = Type.LONG;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for >>>");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        if(type==Type.INT){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IUSHR);
        }
        else if(type==Type.LONG){
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LUSHR);
        }
    }
}

